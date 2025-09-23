package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import dao.DependenteDAO;
import dao.FolhaPagamentoDAO;
import dao.FuncionarioDAO;
import model.Dependente;
import model.FolhaPagamento;
import model.Funcionario;
import model.Parentesco;
import service.CalculadoraSalario;
import service.CalculoService;
import service.DatabaseService;

public class TelaFolhaPagamento extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nomeField, cpfField, nascimentoField, salarioField;
    private JTextArea resultadoArea;
    private List<Dependente> dependentes = new ArrayList<>();

    public TelaFolhaPagamento() {
        setTitle("Folha de Pagamento");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelEntradaPrincipal = new JPanel(new BorderLayout(10, 10));

        JPanel painelCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        painelCampos.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        painelCampos.add(nomeField);

        painelCampos.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        painelCampos.add(cpfField);

        painelCampos.add(new JLabel("Data de Nascimento (yyyy-MM-dd):"));
        nascimentoField = new JTextField();
        painelCampos.add(nascimentoField);

        painelCampos.add(new JLabel("Salário Bruto:"));
        salarioField = new JTextField();
        painelCampos.add(salarioField);
        
        painelEntradaPrincipal.add(painelCampos, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();
        JButton adicionarDepBtn = new JButton("Adicionar Dependente");
        JButton calcularBtn = new JButton("Calcular e Salvar");
        painelBotoes.add(adicionarDepBtn);
        painelBotoes.add(calcularBtn);

        painelEntradaPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelEntradaPrincipal, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        adicionarDepBtn.addActionListener(e -> abrirDialogDependente());
        calcularBtn.addActionListener(e -> processarFuncionario());
    }

    private void abrirDialogDependente() {
        JTextField nomeDep = new JTextField();
        JTextField cpfDep = new JTextField();
        JTextField nascDep = new JTextField();
        JComboBox<Parentesco> parentescoBox = new JComboBox<>(Parentesco.values());

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeDep);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfDep);
        panel.add(new JLabel("Nascimento (yyyy-MM-dd):"));
        panel.add(nascDep);
        panel.add(new JLabel("Parentesco:"));
        panel.add(parentescoBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "Novo Dependente", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Dependente dep = new Dependente(
                    nomeDep.getText(),
                    cpfDep.getText(),
                    LocalDate.parse(nascDep.getText()),
                    (Parentesco) parentescoBox.getSelectedItem()
                );
                dependentes.add(dep);
                JOptionPane.showMessageDialog(this, "Dependente adicionado com sucesso!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro nos dados do dependente: " + ex.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void processarFuncionario() {
        String nome = nomeField.getText();
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro: O campo Nome é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String cpf = cpfField.getText();
        if (cpf == null || !cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Erro: CPF inválido. Deve conter 11 dígitos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LocalDate dataNascimento;
        try {
            dataNascimento = LocalDate.parse(nascimentoField.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: Data de Nascimento inválida. Use o formato yyyy-MM-dd.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        double salarioBruto;
        try {
            salarioBruto = Double.parseDouble(salarioField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: Salário Bruto inválido. Use apenas números.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection conn = null;
        try {
            conn = DatabaseService.getConnection();
            conn.setAutoCommit(false);

            Funcionario func = new Funcionario(nome, cpf, dataNascimento, salarioBruto);
            for (Dependente dep : dependentes) {
                func.addDependente(dep);
            }

            int funcId = FuncionarioDAO.salvar(conn, func);
            for (Dependente dep : dependentes) {
                DependenteDAO.salvar(conn, dep, funcId);
            }

            CalculoService calcService = new CalculadoraSalario();
            double liquido = calcService.calcularSalarioLiquido(func);

            FolhaPagamento folha = new FolhaPagamento(func, LocalDate.now());
            folha.setDescontoINSS(func.getDescontoINSS());
            folha.setDescontoIR(func.getDescontoIR());
            folha.setSalarioLiquido(liquido);
            FolhaPagamentoDAO.salvar(conn, folha, funcId);
            
            conn.commit();

            resultadoArea.setText(String.format(
                "✅ Folha gerada para %s com sucesso!\nINSS: R$ %.2f\nIR: R$ %.2f\nSalário Líquido: R$ %.2f",
                func.getNome(), func.getDescontoINSS(), func.getDescontoIR(), liquido
            ));
            dependentes.clear();

        } catch (SQLException ex) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException e) { e.printStackTrace(); }
            }
            JOptionPane.showMessageDialog(this, "Erro no Banco de Dados: " + ex.getMessage(), "Erro de Persistência", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception ex) {
             JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado: " + ex.getMessage(), "Erro Geral", JOptionPane.ERROR_MESSAGE);
             ex.printStackTrace();
        } finally {
            if (conn != null) {
                try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaFolhaPagamento().setVisible(true));
    }
}