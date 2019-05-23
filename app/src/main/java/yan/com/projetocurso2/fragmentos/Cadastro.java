package yan.com.projetocurso2.fragmentos;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import de.hdodenhof.circleimageview.CircleImageView;
import yan.com.projetocurso2.R;

public class Cadastro extends Fragment {

    EditText txtCliente, txtEndereco, txtNumero, txtBairro, txtCidade;
    CircleImageView circleImageView;
    AlertDialog alerta;
    ImageView img;
    Button btnSalvar;
    Spinner spinnerOpcao;

    Long id;

    private OnFragmentInteractionListener mListener;

    public Cadastro() {}

    public static Cadastro cadastroId(Long ID)
    {
        Cadastro fragment = new Cadastro();
        Bundle args = new Bundle();
        args.putLong("id",ID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_cadastro, container, false);

        circleImageView = v.findViewById(R.id.imgView_Servico);
        img = v.findViewById(R.id.img_exibir);
        txtCliente = v.findViewById(R.id.txt_cadastro_Cliente);
        txtEndereco = v.findViewById(R.id.txt_cadastro_Endereco);
        txtNumero = v.findViewById(R.id.txt_cadastro_Numero);
        txtBairro = v.findViewById(R.id.txt_cadastro_Bairro);
        txtCidade = v.findViewById(R.id.txt_cadastro_Cidade);
        spinnerOpcao = v.findViewById(R.id.spinner_cadastro_Opcao);
        btnSalvar = v.findViewById(R.id.btnSalvar);

        readArgs(getArguments());

        return v;
    }

    void readArgs(Bundle bundle)
    {
        if(bundle != null)
        {
            id = bundle.getLong("id");
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
