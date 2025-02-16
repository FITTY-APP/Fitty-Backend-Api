package fittibackendapp.domain.body.service

import fittibackendapp.domain.auth.repository.UserRepository
import fittibackendapp.domain.body.entity.UserDetail
import fittibackendapp.domain.body.graphql.mutation.input.PutUserDetailInput
import fittibackendapp.domain.body.repository.UserDetailRepository
import fittibackendapp.dto.UserDetailDto
import fittibackendapp.dto.mapstruct.UserDetailMapStruct
import fittibackendapp.exception.NotFoundUserDetailException
import fittibackendapp.exception.NotFoundUserException
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserDetailService(
    private val userDetailRepository: UserDetailRepository,
    private val userRepository: UserRepository,
    private val userDetailMapStruct: UserDetailMapStruct,
) {
    @Transactional
    fun putUserDetail(
        putUserDetailInput: PutUserDetailInput,
        userId: Long,
    ): UserDetailDto {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundUserException()

        val userDetail = if (putUserDetailInput.id != null) {
            userDetailRepository.findByIdOrNull(putUserDetailInput.id)
                ?.apply {
                    this.age = putUserDetailInput.age
                    this.height = putUserDetailInput.height
                    this.weight = putUserDetailInput.weight
                    this.muscleMass = putUserDetailInput.muscleMass
                    this.bodyFat = putUserDetailInput.bodyFat
                    this.targetWeight = putUserDetailInput.targetWeight
                    this.targetMuscleMass = putUserDetailInput.targetMuscleMass
                    this.recordTime = putUserDetailInput.recordTime
                    userDetailRepository.save(this)
                } ?: throw NotFoundUserDetailException()
        }
        else {
            userDetailRepository.save(
                UserDetail(
                    user = user,
                    age = putUserDetailInput.age,
                    height = putUserDetailInput.height,
                    weight = putUserDetailInput.weight,
                    muscleMass = putUserDetailInput.muscleMass,
                    bodyFat = putUserDetailInput.bodyFat,
                    targetWeight = putUserDetailInput.targetWeight,
                    targetMuscleMass = putUserDetailInput.targetMuscleMass,
                    recordTime = putUserDetailInput.recordTime,
                ),
            )
        }
        return userDetailMapStruct.toDto(userDetail)
    }
}
