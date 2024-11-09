package com.kakaotech.team25.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kakaotech.team25.databinding.ActivityLoginEntryBinding
import com.kakaotech.team25.ui.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakaotech.team25.domain.Role
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Instant

@AndroidEntryPoint
class LoginEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEntryBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAutoLogin()
        setKakaoLoginBtnClickListener()
        observeLoginState()
    }

    private fun checkAutoLogin() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val tokens = loginViewModel.getSavedTokens()
                if (tokens != null && tokens.accessToken.isNotEmpty()) {
                    val currentTime = Instant.now().epochSecond * 1000
                    val expirationTime = tokens.loginTime * 1000 + tokens.refreshTokenExpiresIn
                    val timeThreshold = 7 * 24 * 60 * 60000L

                    if (currentTime >= expirationTime - timeThreshold) {
                        binding.kakaoLoginBtn.visibility = View.VISIBLE
                    } else {
                        val role = loginViewModel.getUserRole()
                        navigateBasedOnRole(role)
                    }
                } else {
                    binding.kakaoLoginBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setKakaoLoginBtnClickListener() {
        binding.kakaoLoginBtn.setOnClickListener {
            handleKakaoLogin()
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            loginViewModel.loginState.collect { state ->
                when (state) {
                    is LoginState.Loading -> {
                        // 로딩 상태 처리 (프로그레스바 표시)
                    }

                    is LoginState.Success -> {
                        Log.i(TAG, "로그인 성공")
                    }

                    is LoginState.Error -> {
                        binding.loginErrorTextView.text = state.message
                    }

                    LoginState.Idle -> {}
                }
            }
        }
    }

    private fun handleKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오 계정으로 로그인 실패", error)
                loginViewModel.updateErrorMessage("카카오 로그인 실패")
            } else if (token != null) {
                Log.i(TAG, "카카오 계정으로 로그인 성공 ${token.accessToken}")
                fetchUserInfo(token.accessToken)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오톡으로 로그인 실패", error)

                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    fetchUserInfo(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun fetchUserInfo(accessToken: String) {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(TAG, "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(TAG, "사용자 정보 요청 성공: ${user.kakaoAccount?.profile?.nickname}, ${user.id}")

                loginViewModel.login(accessToken)
                Log.d(TAG, accessToken)
                navigateToMain()
            }
        }
    }

    private fun navigateBasedOnRole(role: Role?) {
        if (role == Role.ROLE_USER) {
            navigateToMain()
        } else {
            binding.kakaoLoginBtn.visibility = View.VISIBLE
        }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val TAG = "kakaoLogin"
    }
}
