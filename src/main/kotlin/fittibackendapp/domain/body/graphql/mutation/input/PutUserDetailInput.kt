package fittibackendapp.domain.body.graphql.mutation.input

data class PutUserDetailInput(
    val id: Long?,
    val age: Int?,
    val height: Double?,
    val weight: Double?,
    val muscleMass: Double?,
    val bodyFat: Double?,
    val targetWeight: Double?,
    val targetMuscleMass: Double?,
)
