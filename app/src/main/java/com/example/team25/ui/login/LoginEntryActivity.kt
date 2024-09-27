package com.example.team25.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.team25.databinding.ActivityLoginEntryBinding
import com.example.team25.ui.main.MainActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginEntryBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setKakaoLoginBtnClickListener()
        observeLoginUiState()
    }

    private fun setKakaoLoginBtnClickListener() {
        binding.kakaoLoginBtn.setOnClickListener {
            loginViewModel.kakaoLogin()
        }
    }

    private fun observeLoginUiState() {
        lifecycleScope.launch {
            loginViewModel.socialLoginUiState.collect { uiState ->
                when (uiState) {
                    SocialLoginUiState.KakaoSocialLoginUi -> {
                        handleKakaoLogin()
                    }
                    SocialLoginUiState.SocialLoginUiSuccess -> {
                        navigateToMainActivity()
                    }
                    SocialLoginUiState.SocialLoginUiFail -> {
                        showToast("Login Failed")
                    }
                    SocialLoginUiState.IDle -> { }
                }
            }
        }
    }

    private fun handleKakaoLogin() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "카카오계정으로 로그인 실패", error)
                loginViewModel.kakaoLoginFail()
            } else if (token != null) {
                Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e(TAG, "사용자 정보 요청 실패", error)
                        loginViewModel.kakaoLoginFail()
                    } else if (user != null) {
                        Log.i(TAG, "사용자 정보 요청 성공: ${user.kakaoAccount?.profile?.nickname}, ${user.id}")

                        // 로그인 성공 시 처리
                        loginViewModel.kakaoLoginSuccess()
                    }
                }
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
                    loginViewModel.kakaoLoginSuccess()

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                        } else if (user != null) {
                            Log.i(TAG, "사용자 정보 요청 성공: ${user.kakaoAccount?.profile?.nickname}, ${user.id}")

                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("user_nickname", user.kakaoAccount?.profile?.nickname)
                            intent.putExtra("user_id", user.id)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "kakaoLogin"
    }
}
