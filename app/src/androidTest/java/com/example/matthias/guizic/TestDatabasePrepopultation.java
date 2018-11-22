package com.example.matthias.guizic;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.matthias.guizic.Database.AppDatabase;
import com.example.matthias.guizic.Database.SecretZone;
import com.example.matthias.guizic.Database.SecretZoneDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(AndroidJUnit4.class)
public class TestDatabasePrepopultation {

    private SecretZoneDao mSecretZoneDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mSecretZoneDao = mDb.secretZoneDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void test() throws Exception{
        SecretZone secretZone = new SecretZone(-1.636408, 48.118096, 1.3, "Esir", "E");
        mDb.secretZoneDao().insertAll(secretZone);
        SecretZone secretZone2 = new SecretZone(-1.63648, 48.11806, 1.3, "Esir2", "E2");
        mDb.secretZoneDao().insertAll(secretZone);
        List<SecretZone> secretZones = mSecretZoneDao.getAll();
        assertEquals(2, secretZones.size());
    }
}
