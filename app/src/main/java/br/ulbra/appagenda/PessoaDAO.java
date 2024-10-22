package br.ulbra.appagenda;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ulbra.appagenda.Conexao;
import br.ulbra.appagenda.Pessoa;

public class PessoaDAO {
    private Conexao conexao; // Referência à classe Conexao
    private SQLiteDatabase banco; // Banco de dados

    public PessoaDAO(Context context) {
        // Inicializa a conexão com o banco de dados
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("telefone", pessoa.getTelefone());
        return banco.insert("pessoa", null, values);
    }

    public List<Pessoa> obterTodos() {
        List<Pessoa> pessoas = new ArrayList<>();
        Cursor cursor = banco.query("pessoa",
                new String[]{"id", "nome", "cpf", "telefone"},
                null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Pessoa p = new Pessoa();
                int idIndex = cursor.getColumnIndex("id");
                int nomeIndex = cursor.getColumnIndex("nome");
                int cpfIndex = cursor.getColumnIndex("cpf");
                int telefoneIndex = cursor.getColumnIndex("telefone");
                pessoas.add(p);
            } while (cursor.moveToNext());
            cursor.close(); // Fecha o cursor após o uso
        }
        return pessoas;
    }

    public void atualizar(Pessoa pessoa) {
        ContentValues values = new ContentValues();
        values.put("nome", pessoa.getNome());
        values.put("cpf", pessoa.getCpf());
        values.put("telefone", pessoa.getTelefone());
        banco.update("pessoa", values, "id = ?", new String[]{String.valueOf(pessoa.getId())});
    }

    public void excluir(Pessoa pessoa) {
        banco.delete("pessoa", "id = ?", new String[]{String.valueOf(pessoa.getId())});
    }

    public void fechar() {
        if (banco != null && banco.isOpen()) {
            banco.close();
        }
        if (conexao != null) {
            conexao.close();
        }
    }
}
