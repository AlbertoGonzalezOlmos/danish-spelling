package com.example.danishspelling.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.danishspelling.data.local.SpellingDatabase
import com.example.danishspelling.data.local.dao.*
import com.example.danishspelling.data.local.entities.PracticeList
import com.example.danishspelling.data.local.entities.Sentence
import com.example.danishspelling.data.local.entities.UserSettings
import com.example.danishspelling.domain.model.DifficultyLevel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSpellingDatabase(
        @ApplicationContext context: Context,
        provider: Provider<PracticeListDao>,
        providerSentence: Provider<SentenceDao>,
        providerSettings: Provider<UserSettingsDao>
    ): SpellingDatabase {
        return Room.databaseBuilder(
            context,
            SpellingDatabase::class.java,
            SpellingDatabase.DATABASE_NAME
        )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    // Prepopulate database with initial data
                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
                        val listDao = provider.get()
                        val sentenceDao = providerSentence.get()
                        val settingsDao = providerSettings.get()

                        // Create default user settings
                        settingsDao.insert(UserSettings())

                        // Create sample practice lists with Danish sentences
                        val animalListId = listDao.insert(
                            PracticeList(
                                name = "Dyr (Animals)",
                                description = "Lær at stave dyrenavne",
                                difficultyLevel = DifficultyLevel.EASY,
                                colorTheme = "#4CAF50",
                                iconName = "animals"
                            )
                        )

                        val familyListId = listDao.insert(
                            PracticeList(
                                name = "Familie (Family)",
                                description = "Familiemedlemmer",
                                difficultyLevel = DifficultyLevel.EASY,
                                colorTheme = "#2196F3",
                                iconName = "family"
                            )
                        )

                        val schoolListId = listDao.insert(
                            PracticeList(
                                name = "Skole (School)",
                                description = "Ord fra skolen",
                                difficultyLevel = DifficultyLevel.MEDIUM,
                                colorTheme = "#FF9800",
                                iconName = "school"
                            )
                        )

                        // Insert sample sentences for Animals list
                        sentenceDao.insertAll(
                            listOf(
                                Sentence(
                                    listId = animalListId,
                                    text = "Hunden løber i parken",
                                    difficulty = 1,
                                    orderIndex = 0
                                ),
                                Sentence(
                                    listId = animalListId,
                                    text = "Katten sover på sofaen",
                                    difficulty = 1,
                                    orderIndex = 1
                                ),
                                Sentence(
                                    listId = animalListId,
                                    text = "Fuglen synger i træet",
                                    difficulty = 2,
                                    orderIndex = 2
                                ),
                                Sentence(
                                    listId = animalListId,
                                    text = "Fisken svømmer i vandet",
                                    difficulty = 1,
                                    orderIndex = 3
                                ),
                                Sentence(
                                    listId = animalListId,
                                    text = "Kaninen hopper rundt",
                                    difficulty = 2,
                                    orderIndex = 4
                                )
                            )
                        )

                        // Insert sample sentences for Family list
                        sentenceDao.insertAll(
                            listOf(
                                Sentence(
                                    listId = familyListId,
                                    text = "Min mor er sød",
                                    difficulty = 1,
                                    orderIndex = 0
                                ),
                                Sentence(
                                    listId = familyListId,
                                    text = "Min far læser en bog",
                                    difficulty = 2,
                                    orderIndex = 1
                                ),
                                Sentence(
                                    listId = familyListId,
                                    text = "Min søster leger med dukker",
                                    difficulty = 2,
                                    orderIndex = 2
                                ),
                                Sentence(
                                    listId = familyListId,
                                    text = "Min bror spiller fodbold",
                                    difficulty = 2,
                                    orderIndex = 3
                                ),
                                Sentence(
                                    listId = familyListId,
                                    text = "Hele familien spiser sammen",
                                    difficulty = 3,
                                    orderIndex = 4
                                )
                            )
                        )

                        // Insert sample sentences for School list
                        sentenceDao.insertAll(
                            listOf(
                                Sentence(
                                    listId = schoolListId,
                                    text = "Jeg går i tredje klasse",
                                    difficulty = 3,
                                    orderIndex = 0
                                ),
                                Sentence(
                                    listId = schoolListId,
                                    text = "Vi har matematik i dag",
                                    difficulty = 3,
                                    orderIndex = 1
                                ),
                                Sentence(
                                    listId = schoolListId,
                                    text = "Læreren er meget venlig",
                                    difficulty = 3,
                                    orderIndex = 2
                                ),
                                Sentence(
                                    listId = schoolListId,
                                    text = "Jeg læser en spændende bog",
                                    difficulty = 3,
                                    orderIndex = 3
                                ),
                                Sentence(
                                    listId = schoolListId,
                                    text = "Frikvarteret er sjovt",
                                    difficulty = 3,
                                    orderIndex = 4
                                )
                            )
                        )
                    }
                }
            })
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePracticeListDao(database: SpellingDatabase): PracticeListDao {
        return database.practiceListDao()
    }

    @Provides
    fun provideSentenceDao(database: SpellingDatabase): SentenceDao {
        return database.sentenceDao()
    }

    @Provides
    fun providePracticeSessionDao(database: SpellingDatabase): PracticeSessionDao {
        return database.practiceSessionDao()
    }

    @Provides
    fun providePracticeAttemptDao(database: SpellingDatabase): PracticeAttemptDao {
        return database.practiceAttemptDao()
    }

    @Provides
    fun provideUserSettingsDao(database: SpellingDatabase): UserSettingsDao {
        return database.userSettingsDao()
    }
}
