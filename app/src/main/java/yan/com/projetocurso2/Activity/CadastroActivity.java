package yan.com.projetocurso2.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.*;
import com.squareup.picasso.Picasso;
import yan.com.projetocurso2.DAO.Banco;
import yan.com.projetocurso2.DAO.ServicoDao;
import yan.com.projetocurso2.Model.Servico;
import yan.com.projetocurso2.fragmentos.MostrarImagem;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import de.hdodenhof.circleimageview.CircleImageView;
import yan.com.projetocurso2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends Activity {

    CircleImageView circleImageView;
    AlertDialog alerta;
    ImageView img;
    Button btnSalvar;
    Intent intent;
    EditText txtCliente, txtEndereco, txtNumero, txtBairro, txtCidade;
    Spinner spinnerOpcao;
    Servico servicoAtual;
    boolean atualizando = false, mudouImagem = false;
    File foto , image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //region Views

        setContentView(R.layout.activity_cadastro);
        circleImageView = findViewById(R.id.imgView_Servico);
        img = findViewById(R.id.img_exibir);
        intent = getIntent();
        txtCliente = findViewById(R.id.txt_cadastro_Cliente);
        txtEndereco = findViewById(R.id.txt_cadastro_Endereco);
        txtNumero = findViewById(R.id.txt_cadastro_Numero);
        txtBairro = findViewById(R.id.txt_cadastro_Bairro);
        txtCidade = findViewById(R.id.txt_cadastro_Cidade);
        spinnerOpcao = findViewById(R.id.spinner_cadastro_Opcao);
        btnSalvar = findViewById(R.id.btnSalvar);

        //endregion

        foto = new File(getCacheDir(), "image.jpg");

        if (intent.hasExtra("id")) {
            atualizando = true;
            btnSalvar.setText("Atualizar");

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    servicoAtual = Banco.getPersonDatabase(CadastroActivity.this).servicoDatabase().getServicoById(intent.getLongExtra("id", 0));

                    txtCliente.setText(servicoAtual.getCliente());
                    txtEndereco.setText(servicoAtual.getRua());
                    txtCidade.setText(servicoAtual.getCidade());
                    txtNumero.setText(servicoAtual.getNumero());
                    txtBairro.setText(servicoAtual.getBairro());

                    switch (servicoAtual.getTipo()) {
                        case "Instalação":

                            spinnerOpcao.setSelection(0);

                            break;

                        case "Reparo":

                            spinnerOpcao.setSelection(1);

                            break;

                        case "Remoção":

                            spinnerOpcao.setSelection(2);

                            break;
                    }

                }
            });

            Bitmap bmp = BitmapFactory.decodeFile(getFilesDir() + "/" + intent.getLongExtra("id", 0) + "/foto.jpeg");

            circleImageView.setImageBitmap(bmp);

            circleImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LayoutInflater li = getLayoutInflater();

                    View view = li.inflate(R.layout.exibir_imagem_servico, null);


                    AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
                    builder.setView(view);
                    alerta = builder.create();
                    alerta.show();

                    ImageView imageView = view.findViewById(R.id.img_exibir);

                    circleImageView.setDrawingCacheEnabled(true);
                    imageView.setImageBitmap(circleImageView.getDrawingCache());


                }
            });
        }

    }

    public void BtnMudarImagem(View v)
    {
       image = new File(getCacheDir(), "image.jpg");

        if(image.exists())
        {
            image.delete();
        }

        LayoutInflater li = getLayoutInflater();

        View view = li.inflate(R.layout.dialogo_imagem, null);

        view.findViewById(R.id.Dialog_Camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(CadastroActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CadastroActivity.this, new String[]{Manifest.permission.CAMERA}, 101);
                    return;
                }

                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                image = new File(getCacheDir(), "image.jpg");
                Uri uriSavedImage = FileProvider.getUriForFile(CadastroActivity.this, "yan.com.projetocurso2.fileprovider", image);
                i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                startActivityForResult(i, 502);

            }
        });

        view.findViewById(R.id.Dialog_Galeria).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType("image/*");

                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.putExtra("mudou", true);
                startActivityForResult(intent, 503);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(CadastroActivity.this);
        builder.setTitle("Escolha uma opção");
        builder.setView(view);
        alerta = builder.create();
        alerta.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        alerta.cancel();

        switch (requestCode) {
            case 502:

                if (resultCode == RESULT_OK)
                {


                    if (foto.exists()) {
                        Picasso.get().load("file:" + foto.getAbsolutePath()).noFade().resize(200, 200).centerCrop().into(circleImageView);
                        mudouImagem = true;
                    }
                }

                break;

            case 503:


                if (resultCode == RESULT_OK && data != null) {
                    Picasso.get().load(data.getData()).into(circleImageView);
                    mudouImagem = true;
                }

                break;

        }

    }

    public void Salvar(View v) {
        Toast.makeText(this, "Aguarde...", Toast.LENGTH_SHORT).show();

        if (!atualizando) {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Servico ser = new Servico
                            (
                                    spinnerOpcao.getSelectedItem().toString(), txtCliente.getText().toString()
                                    , txtNumero.getText().toString(), txtBairro.getText().toString(), txtCidade.getText().toString(), txtEndereco.getText().toString()
                            );

                    Long id = Banco.getPersonDatabase(CadastroActivity.this).servicoDatabase().Insert(ser);

                    if (id > 0) {
                        try {
                            if (mudouImagem) {
                                File fotoPermanente = new File(getFilesDir() + "/" + id.toString());
                                fotoPermanente.mkdirs();
                                FileOutputStream out = new FileOutputStream(fotoPermanente.getAbsoluteFile() + "/foto.jpeg");

                                circleImageView.setDrawingCacheEnabled(true);
                                Bitmap bmp = circleImageView.getDrawingCache();
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                out.flush();
                                out.close();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(foto.exists())
                        {
                            foto.delete();
                        }

                        startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                    }
                }
            });
        } else {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {

                    servicoAtual.setTipo(spinnerOpcao.getSelectedItem().toString());
                    servicoAtual.setBairro(txtBairro.getText().toString());
                    servicoAtual.setCidade(txtCidade.getText().toString());
                    servicoAtual.setCliente(txtCliente.getText().toString());
                    servicoAtual.setNumero(txtNumero.getText().toString());
                    servicoAtual.setRua(txtEndereco.getText().toString());

                    Integer id = Banco.getPersonDatabase(CadastroActivity.this).servicoDatabase().Atualizar(servicoAtual);

                    if (id > 0) {
                        try {
                            if (mudouImagem)
                            {
                                File fotoPermanente = new File(getFilesDir() + "/" + intent.getLongExtra("id", 0));

                                fotoPermanente.mkdirs();
                                FileOutputStream out = new FileOutputStream(fotoPermanente.getAbsolutePath() + "/foto.jpeg");

                                circleImageView.setDrawingCacheEnabled(true);
                                Bitmap bmp = circleImageView.getDrawingCache();
                                bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                out.flush();
                                out.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(foto.exists())
                        {
                            foto.delete();
                        }

                        startActivity(new Intent(CadastroActivity.this, MainActivity.class));
                    }
                }
            });
        }
    }
}
