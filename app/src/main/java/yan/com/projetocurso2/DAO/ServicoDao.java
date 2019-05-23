package yan.com.projetocurso2.DAO;

import android.arch.persistence.room.*;
import yan.com.projetocurso2.Model.Servico;

import java.util.List;

@Dao
public interface ServicoDao
{
    @Insert(onConflict = OnConflictStrategy.ABORT)
    long Insert(Servico servico);

    @Update
    int Atualizar(Servico servico);

    @Delete
    int Deletar(Servico servico);

    @Query("SELECT * FROM Servico where Id = :id")
    Servico getServicoById(Long id);

    @Query("SELECT * FROM Servico")
    List<Servico> getTodosServicos();

    @Query("DELETE FROM Servico WHERE Id = :id")
    int deleteServicoById(Long id);


}
