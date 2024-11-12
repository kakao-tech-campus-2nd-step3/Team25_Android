package com.kakaotech.team25.ui.reservation

import android.net.Uri
import android.util.Log
import com.kakaotech.team25.data.network.dto.ProfileData
import com.kakaotech.team25.domain.usecase.GetImageUriUseCase
import com.kakaotech.team25.domain.usecase.GetProfileUseCase
import com.kakaotech.team25.data.network.dto.ProfileDto
import com.kakaotech.team25.data.network.dto.WorkingHour
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ManagerProfileViewModelTest {

    private lateinit var viewModel: ManagerProfileViewModel
    private val getProfileUseCase: GetProfileUseCase = mockk()
    private val getImageUriUseCase: GetImageUriUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        // ViewModel 초기화
        Dispatchers.setMain(testDispatcher)
        viewModel = ManagerProfileViewModel(getProfileUseCase, getImageUriUseCase)
        mockLogClass()
    }

    @Test
    fun `프로필 데이터 불러오기 테스트`() = runTest {
        // given
        mockkStatic(Uri::class)
        val managerId = "manager123"
        val profileDto = ProfileDto(
            status = true,
            message = "프로필 데이터를 불러왔습니다",
            data = ProfileData(
                name = "홍길동",
                profileImage = "profile_image_url",
                gender = "남성",
                career = "5년 경력",
                comment = "안녕하세요",
                workingRegion = "서울",
                workingHour = WorkingHour(
                    monStartTime = "09:00",
                    monEndTime = "18:00",
                    tueStartTime = "09:00",
                    tueEndTime = "18:00",
                    wedStartTime = "09:00",
                    wedEndTime = "18:00",
                    thuStartTime = "09:00",
                    thuEndTime = "18:00",
                    friStartTime = "09:00",
                    friEndTime = "18:00",
                    satStartTime = "10:00",
                    satEndTime = "14:00",
                    sunStartTime = "10:00",
                    sunEndTime = "14:00"
                )
            )
        )
        coEvery { getImageUriUseCase.invoke("profile_image_url") } returns mockk()
        coEvery { getProfileUseCase(managerId) } returns profileDto

        // when
        viewModel.getProfile(managerId)

        // then
        assertEquals(ManagerProfileViewModel.ProfileLoadStatus.SUCCESS, viewModel.profileLoadStatus.first())
        assertEquals("홍길동", viewModel.name.first())
        assertEquals("서울", viewModel.workingRegion.first())
        assertEquals("5년 경력", viewModel.career.first())
        assertEquals("안녕하세요", viewModel.comment.first())

        coVerify { getProfileUseCase(managerId) }
    }

    @Test
    fun `프로필 데이터 불러오기 실패 테스트`() = runTest {
        // given
        val managerId = "manager123"

        coEvery { getProfileUseCase(managerId) } returns null

        // when
        viewModel.getProfile(managerId)

        // then
        assertEquals(ManagerProfileViewModel.ProfileLoadStatus.FAILURE, viewModel.profileLoadStatus.first())

        // verify 호출 확인
        coVerify { getProfileUseCase(managerId) }
    }

    private fun mockLogClass() {
        mockkStatic(Log::class)
        every { Log.v(any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

}
