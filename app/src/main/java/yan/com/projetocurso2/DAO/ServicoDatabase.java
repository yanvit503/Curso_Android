package yan.com.projetocurso2.DAO;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import yan.com.projetocurso2.Model.Servico;

@Database(entities = {Servico.class}, version = 1)
public abstract class ServicoDatabase extends RoomDatabase {

    public abstract ServicoDao servicoDatabase();

}
