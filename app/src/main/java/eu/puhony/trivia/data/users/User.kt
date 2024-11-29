package eu.puhony.trivia.data.users

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,

    //TODO: Can I assign default value automatically?
    val createdAt: Date,
)
