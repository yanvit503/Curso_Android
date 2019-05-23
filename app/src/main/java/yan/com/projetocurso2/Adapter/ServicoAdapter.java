package yan.com.projetocurso2.Adapter;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import yan.com.projetocurso2.Activity.CadastroActivity;
import yan.com.projetocurso2.DAO.Banco;
import yan.com.projetocurso2.Model.Servico;
import yan.com.projetocurso2.R;
import java.util.List;

public class ServicoAdapter extends RecyclerView.Adapter<ServicoAdapter.ServicoViewHolder> {

    List<Servico> servicos;
    AppCompatActivity activity;

    public ServicoAdapter(List<Servico> listaDeServico , AppCompatActivity  a)
    {
        this.servicos = listaDeServico;
        this.activity = a;
    }

    @Override
    public int getItemCount() {
        return servicos.size();
    }


    @Override
    public ServicoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ServicoViewHolder svh = new ServicoViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(final ServicoViewHolder servicoViewHolder,final int i) {
        servicoViewHolder.Tipo.setText(servicos.get(i).getTipo());
        servicoViewHolder.Ordem.setText(servicos.get(i).getId().toString());
        servicoViewHolder.Cliente.setText(servicos.get(i).getCliente());

        servicoViewHolder.btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(activity, "Abrindo Google Maps...", Toast.LENGTH_SHORT).show();
                
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run()
                    {
                        Servico escolhido = Banco.getPersonDatabase(activity.getApplicationContext()).servicoDatabase().getServicoById(Long.parseLong(servicoViewHolder.Ordem.getText().toString()));

                        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+ escolhido.getEnderecoTotal());
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        activity.startActivity(mapIntent);
                    }
                });
                
            }
        });

        servicoViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =  new Intent(activity.getApplicationContext(), CadastroActivity.class);

                i.putExtra("id", Long.parseLong(servicoViewHolder.Ordem.getText().toString()));

                activity.startActivity(i);

            }
        });

        servicoViewHolder.cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v)
            {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run()
                    {
                        int qnt = Banco.getPersonDatabase(activity.getApplicationContext()).servicoDatabase().deleteServicoById(Long.parseLong(servicoViewHolder.Ordem.getText().toString()));

                        if(qnt > 0)
                        {

                            servicos.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i,servicos.size());

                        }

                    }
                });

                return true;
            }
        });

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    static class ServicoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView Tipo , Ordem , Cliente;
        ImageButton btnMapa;

        ServicoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card);
            Cliente = (TextView)itemView.findViewById(R.id.card_textoCliente);
            Ordem = (TextView)itemView.findViewById(R.id.card_textoOrdem);
            Tipo = (TextView)itemView.findViewById(R.id.card_textoTipo);
            btnMapa = itemView.findViewById(R.id.card_Btnmapa);

        }
    }
}
