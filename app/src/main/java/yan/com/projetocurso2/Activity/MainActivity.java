package yan.com.projetocurso2.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import yan.com.projetocurso2.Adapter.ServicoAdapter;
import yan.com.projetocurso2.DAO.Banco;
import yan.com.projetocurso2.Model.Servico;
import yan.com.projetocurso2.R;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    RecyclerView recyclerView;
    ServicoAdapter adapter;
    List<Servico> servicos;
    LinearLayoutManager llm;
    FloatingActionButton fab;
    DrawerLayout drwlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar;

        NavigationView nvgtview;

        //region Configuração da Activity

        toolbar = (Toolbar) findViewById(R.id.mainactivity_toolbar);
        setSupportActionBar(toolbar);

        //inicializando o drawer layout
        drwlayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //toggle
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle
                (this, drwlayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drwlayout.addDrawerListener(toogle);

        toogle.syncState();
        toogle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.Branco));

        //click do navigation
        nvgtview = (NavigationView) findViewById(R.id.navigationView);
        nvgtview.setNavigationItemSelectedListener(MainActivity.this);

        //endregion

        recyclerView = findViewById(R.id.recyclerView_Main);
        servicos = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        llm = new LinearLayoutManager(MainActivity.this);
        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                irParaCadastro();

            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                servicos = Banco.getPersonDatabase(MainActivity.this).servicoDatabase().getTodosServicos();
                adapter = new ServicoAdapter(servicos,( MainActivity.this));
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(llm);

            }
        });

    }

    private void irParaCadastro() {

        startActivity(new Intent(MainActivity.this,CadastroActivity.class));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.novo:

                irParaCadastro();

            break;

            case R.id.sair:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Deseja sair ?");
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Sair",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        moveTaskToBack(true);
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                        System.exit(1);
                                    }
                                })

                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            break;

        }
        drwlayout.closeDrawer(GravityCompat.START);
        return true;

    }
}
