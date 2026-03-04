package com.oscarvera.techtest

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.oscarvera.techtest.data.local.dao.FavoriteDao
import com.oscarvera.techtest.data.local.db.AppDatabase
import com.oscarvera.techtest.data.local.entity.FavoriteEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteDaoIntegrationTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = db.favoriteDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    /**
     * GIVEN an empty in-memory Room database
     * WHEN a favorite is inserted through the DAO
     * THEN the favorite id flow contains the id and the count is updated to 1
     *
     * WHEN the favorite is deleted
     * THEN the favorite id flow no longer contains the id and the count returns to 0
     */
    @Test
    fun insert_and_delete_favorite_updates_count_and_ids() = runTest {
        val fav = FavoriteEntity(
            productId = 1,
            title = "Test",
            image = "img",
            price = 10.0
        )

        // Add
        dao.upsert(fav)

        val idsAfterInsert = dao.observeFavoriteIds().first()
        assertTrue(idsAfterInsert.contains(1))

        val countAfterInsert = dao.observeFavoriteCount().first()
        assertEquals(1, countAfterInsert)

        // Delete
        dao.deleteByProductId(1)

        val idsAfterDelete = dao.observeFavoriteIds().first()
        assertFalse(idsAfterDelete.contains(1))

        val countAfterDelete = dao.observeFavoriteCount().first()
        assertEquals(0, countAfterDelete)
    }
}
