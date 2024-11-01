package br.ulbra.appagenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edNome;
    private EditText edCpf;
    private EditText edTelefone;
    private Button btSalvar;
    private PessoaDAO dao;
    private Pessoa pessoa = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNome = findViewById(R.id.edNome);
        edCpf = findViewById(R.id.edCpf);
        edTelefone = findViewById(R.id.edTelefone);
        btSalvar = findViewById(R.id.btSalvar);

        dao = new PessoaDAO(this);

        Intent it = getIntent();
        if (it.hasExtra("pessoa")) {
            pessoa = (Pessoa) it.getSerializableExtra("pessoa");
            edNome.setText(pessoa.getNome());
            edCpf.setText(pessoa.getCpf());
            edTelefone.setText(pessoa.getTelefone());
        }
    }

    public void salvar(View view) {
        if (edNome.getText().toString().isEmpty() || edCpf.getText().toString().isEmpty() || edTelefone.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show();
            return;
        }

        if (pessoa == null) {
            pessoa = new Pessoa();
        }

        pessoa.setNome(edNome.getText().toString());
        pessoa.setCpf(edCpf.getText().toString());
        pessoa.setTelefone(edTelefone.getText().toString());

        if (pessoa.getId() == 0) {
            long id = dao.inserir(pessoa);
            Toast.makeText(this, "Pessoa inserida com sucesso! ID: " + id, Toast.LENGTH_LONG).show();
        } else {dao.atualizar(pessoa);
            Toast.makeText(this, pessoa.getNome() + " atualizado com sucesso!", Toast.LENGTH_LONG).show();
        }

        limparCampos();
    }

    private void limparCampos() {
        edNome.setText("");
        edCpf.setText("");
        edTelefone.setText("");
        pessoa = null;
    }
}
