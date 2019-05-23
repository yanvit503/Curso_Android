package yan.com.projetocurso2.DAO;

import android.arch.persistence.room.Room;
import android.content.Context;

public class Banco {

    private static ServicoDatabase servicoDatabase;
    private static final Object LOCK = new Object();

    public synchronized static ServicoDatabase getPersonDatabase(Context context){
        if(servicoDatabase == null) {
            synchronized (LOCK) {
                if (servicoDatabase == null) {
                    servicoDatabase = Room.databaseBuilder(context,
                            ServicoDatabase.class, "servico BD").build();
                }
            }
        }
        return servicoDatabase;
    }

}
