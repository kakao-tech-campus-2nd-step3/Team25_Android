package com.kakaotech.team25.ui.reservation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kakaotech.team25.R
import com.kakaotech.team25.data.network.dto.CreateBillingKeyRequest
import com.kakaotech.team25.data.network.services.CardService
import com.kakaotech.team25.databinding.ActivityAddCreditcardBinding
import com.kakaotech.team25.security.CardInformationEncryption
import com.kakaotech.team25.ui.main.status.data.CardInfor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddCreditcardActivity : AppCompatActivity() {
    @Inject
    lateinit var cardService: CardService
    private lateinit var binding: ActivityAddCreditcardBinding
    private val viewModel: AddCreditcardViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCreditcardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        selectAddCreditCard()
    }

    private fun initCardInfor() {
        val creditCardNumber =
            binding.numFirstEditText.text.toString() +
                binding.numSecondEditText.text.toString() +
                binding.numThirdEditText.text.toString() +
                binding.numFourthEditText.text.toString()

        val expireDate = binding.expireDayEditText.text.toString()
        val twoPassword = binding.passwordEditText.text.toString()
        val birth = binding.birthEditText.text.toString()

        val cardInfor = cardService.createCardInfo(creditCardNumber, expireDate, twoPassword, birth)

        if (validateCard(cardInfor).count { it } == 4) {
            presentError(validateCard(cardInfor))
            val encryptedData = CardInformationEncryption().encryptCBC(cardInfor)
            val cardData = CreateBillingKeyRequest(
                encData = encryptedData,
                cardAlias = ""
                )
            viewModel.createBillingKey(cardData)
        } else {
            presentError(validateCard(cardInfor))
        }
    }

    private fun selectAddCreditCard() {
        binding.addBtn.setOnClickListener {
            initCardInfor()
        }
    }

    private fun presentError(array: Array<Boolean>) {
        showError(array[0], binding.inputCardNumberErrorTextView, binding.inputCreditCardLayout)
        showError(array[1], binding.inputExpireDayErrorTextView, binding.expireDayEditText)
        showError(array[2], binding.inputPasswordErrorTextView, binding.passwordEditText)
        showError(array[3], binding.inputCardBirthTextView, binding.birthEditText)
    }

    private fun showError(
        isValid: Boolean,
        errorTextView: View,
        inputField: View,
    ) {
        if (!isValid) {
            errorTextView.visibility = View.VISIBLE
            inputField.setBackgroundResource(R.drawable.edit_text_box_red)
        } else {
            errorTextView.visibility = View.GONE
            inputField.setBackgroundResource(R.drawable.edit_text_box)
        }
    }

    private fun validateCard(card: CardInfor): Array<Boolean> {
        val isCardNumberValid = card.getCardNumber().length in 15..16
        val isExpireDateValid = card.getExpiredDate().length == 4
        val isPasswordValid = card.getPassword().length == 2
        val isBirthValid = card.getBirth().length == 6

        return arrayOf(isCardNumberValid, isExpireDateValid, isPasswordValid, isBirthValid)
    }

    private fun observeViewModel() {
        viewModel.billingKeyResponse.observe(this, Observer { response ->
            response?.let {
                if (it.status!!) {
                    finish()
                } else {
                    Toast.makeText(this.getApplicationContext(),"${it.data!!.resultMsg}", Toast.LENGTH_SHORT).show();
                }
            }
        })
        viewModel.error.observe(this, Observer { errorMessage ->
            errorMessage?.let {

            }
        })
    }
}
