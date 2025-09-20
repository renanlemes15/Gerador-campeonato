/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author renan
 */
public class telaBrasileirao extends javax.swing.JFrame {

    /**
     * Creates new form telaBrasileirao
     */
    public telaBrasileirao() {
        initComponents();
        tableJogos.setVisible(false);
        scrollJogos.setVisible(false);
        tableFinal.setVisible(false);
        scrollFinal.setVisible(false);
        addTimes();
    }

    DefaultListModel<String> modeloLista = new DefaultListModel<>();

    public void addTimes() {
        DefaultListModel<String> modelo = (DefaultListModel) listTimes.getModel();
        String[] listaDeTimes = {
            "Amazonas",
            "América Mineiro",
            "Athletic",
            "Athletico Paranaense",
            "Atlético Goianiense",
            "Avaí",
            "Botafogo-SP",
            "Chapecoense",
            "Coritiba",
            "CRB",
            "Criciúma",
            "Cuiabá",
            "Ferroviária",
            "Goiás",
            "Novorizontino",
            "Operário-PR",
            "Paysandu",
            "Remo",
            "Vila Nova",
            "Volta Redonda"
        };

        for (String time : listaDeTimes) {
            modelo.addElement(time);
        }
    }

    public void ajustarTabelas() {
        tableJogos.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableJogos.getColumnModel().getColumn(1).setPreferredWidth(15);
        tableJogos.getColumnModel().getColumn(2).setPreferredWidth(3);
        tableJogos.getColumnModel().getColumn(3).setPreferredWidth(15);
        tableJogos.getColumnModel().getColumn(4).setPreferredWidth(120);

        tableFinal.getColumnModel().getColumn(0).setPreferredWidth(138);

        JTableHeader cabecalho = tableJogos.getTableHeader();
        cabecalho.setFont(new Font("Arial", Font.BOLD, 14));
        ((DefaultTableCellRenderer) cabecalho.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        centralizado.setFont(new Font("Arial", Font.PLAIN, 12));

        for (int i = 0; i < tableJogos.getColumnCount(); i++) {
            tableJogos.getColumnModel().getColumn(i).setCellRenderer(centralizado);
        }

        JTableHeader cabecalho2 = tableFinal.getTableHeader();
        cabecalho2.setFont(new Font("Arial", Font.BOLD, 14));
        ((DefaultTableCellRenderer) cabecalho2.getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer centralizado2 = new DefaultTableCellRenderer();
        centralizado2.setHorizontalAlignment(SwingConstants.CENTER);
        centralizado2.setFont(new Font("Arial", Font.PLAIN, 12));

        for (int i = 0; i < tableFinal.getColumnCount(); i++) {
            tableFinal.getColumnModel().getColumn(i).setCellRenderer(centralizado2);
        }

    }

    public void geraJogos() {
        DefaultListModel modeloTimes = (DefaultListModel) listTimes.getModel();
        DefaultTableModel modeloTabelaJogos = (DefaultTableModel) tableJogos.getModel();
        int i, j;
        for (i = 0; i < modeloTimes.getSize(); i++) {
            for (j = 0; j < modeloTimes.getSize(); j++) {
                if (modeloTimes.elementAt(i) == modeloTimes.elementAt(j)) {
                    continue;
                }
                int[] placar = geraPlacar();
                modeloTabelaJogos.addRow(new Object[]{modeloTimes.elementAt(i), placar[0], "", placar[1], modeloTimes.elementAt(j)});
            }
        }
    }

    public int[] geraPlacar() {
        Random rand = new Random();
        int timeA = rand.nextInt(7);
        int timeB = rand.nextInt(7);
        return new int[]{timeA, timeB};
    }

    public void atualizaPlacarFinal() {
        DefaultTableModel modeloJogos = (DefaultTableModel) tableJogos.getModel();
        DefaultTableModel modeloFinal = (DefaultTableModel) tableFinal.getModel();

        String[] times = {
            "Amazonas", "América Mineiro", "Athletic", "Athletico Paranaense",
            "Atlético Goianiense", "Avaí", "Botafogo-SP", "Chapecoense",
            "Coritiba", "CRB", "Criciúma", "Cuiabá", "Ferroviária", "Goiás",
            "Novorizontino", "Operário-PR", "Paysandu", "Remo",
            "Vila Nova", "Volta Redonda"
        };

        int[][] tabela = new int[times.length][7];

        for (int i = 0; i < modeloJogos.getRowCount(); i++) {
            String timeA = (String) modeloJogos.getValueAt(i, 0);
            int golsA = Integer.parseInt(modeloJogos.getValueAt(i, 1).toString());
            int golsB = Integer.parseInt(modeloJogos.getValueAt(i, 3).toString());
            String timeB = (String) modeloJogos.getValueAt(i, 4);

            int idxA = Arrays.asList(times).indexOf(timeA);
            int idxB = Arrays.asList(times).indexOf(timeB);

            tabela[idxA][4] += golsA;
            tabela[idxA][5] += golsB;
            tabela[idxB][4] += golsB;
            tabela[idxB][5] += golsA;

            if (golsA > golsB) {
                tabela[idxA][0] += 3;
                tabela[idxA][1]++;
                tabela[idxB][3]++;
            } else if (golsB > golsA) {
                tabela[idxB][0] += 3;
                tabela[idxB][1]++;
                tabela[idxA][3]++;
            } else {
                tabela[idxA][0]++;
                tabela[idxB][0]++;
                tabela[idxA][2]++;
                tabela[idxB][2]++;
            }
        }

        for (int i = 0; i < times.length; i++) {
            tabela[i][6] = tabela[i][4] - tabela[i][5];
        }

        modeloFinal.setRowCount(0);

        for (int i = 0; i < times.length; i++) {
            modeloFinal.addRow(new Object[]{
                times[i],
                tabela[i][0],
                tabela[i][1],
                tabela[i][2],
                tabela[i][3],
                tabela[i][4],
                tabela[i][5],
                tabela[i][6]
            });
        }

//        TableRowSorter<TableModel> sorter = new TableRowSorter<>(modeloFinal);
//        tableFinal.setRowSorter(sorter);
//        sorter.toggleSortOrder(1);
    }

    public void ordenarTabelaFinal() {
        DefaultTableModel modeloFinal = (DefaultTableModel) tableFinal.getModel();

        int rowCount = modeloFinal.getRowCount();
        List<Object[]> linhas = new ArrayList<>();

        for (int i = 0; i < rowCount; i++) {
            Object[] linha = new Object[modeloFinal.getColumnCount()];
            for (int j = 0; j < modeloFinal.getColumnCount(); j++) {
                linha[j] = modeloFinal.getValueAt(i, j);
            }
            linhas.add(linha);
        }

        Collections.sort(linhas, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                Integer pontos1 = (Integer) o1[1];
                Integer pontos2 = (Integer) o2[1];
                return pontos2.compareTo(pontos1);
            }
        });

        modeloFinal.setRowCount(0);
        for (Object[] linha : linhas) {
            modeloFinal.addRow(linha);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listTimes = new javax.swing.JList<>();
        scrollJogos = new javax.swing.JScrollPane();
        tableJogos = new javax.swing.JTable();
        scrollFinal = new javax.swing.JScrollPane();
        tableFinal = new javax.swing.JTable();
        btnGerar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnLimpar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        listTimes.setModel(modeloLista);
        jScrollPane1.setViewportView(listTimes);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 57, 150, 457));

        tableJogos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tableJogos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Local", "", "X", "", "Visitante"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableJogos.getTableHeader().setReorderingAllowed(false);
        scrollJogos.setViewportView(tableJogos);
        if (tableJogos.getColumnModel().getColumnCount() > 0) {
            tableJogos.getColumnModel().getColumn(0).setResizable(false);
            tableJogos.getColumnModel().getColumn(1).setResizable(false);
            tableJogos.getColumnModel().getColumn(2).setResizable(false);
            tableJogos.getColumnModel().getColumn(3).setResizable(false);
            tableJogos.getColumnModel().getColumn(4).setResizable(false);
        }

        getContentPane().add(scrollJogos, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 14, 640, 240));

        tableFinal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Time", "P", "V", "E", "D", "GP", "GN", "SG"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tableFinal.getTableHeader().setReorderingAllowed(false);
        scrollFinal.setViewportView(tableFinal);
        if (tableFinal.getColumnModel().getColumnCount() > 0) {
            tableFinal.getColumnModel().getColumn(0).setResizable(false);
            tableFinal.getColumnModel().getColumn(1).setResizable(false);
            tableFinal.getColumnModel().getColumn(2).setResizable(false);
            tableFinal.getColumnModel().getColumn(3).setResizable(false);
            tableFinal.getColumnModel().getColumn(4).setResizable(false);
            tableFinal.getColumnModel().getColumn(5).setResizable(false);
            tableFinal.getColumnModel().getColumn(6).setResizable(false);
            tableFinal.getColumnModel().getColumn(7).setResizable(false);
        }

        getContentPane().add(scrollFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 270, 640, 350));

        btnGerar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnGerar.setText("Gerar");
        btnGerar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGerar.setMaximumSize(new java.awt.Dimension(87, 31));
        btnGerar.setMinimumSize(new java.awt.Dimension(87, 31));
        btnGerar.setPreferredSize(new java.awt.Dimension(87, 31));
        btnGerar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarActionPerformed(evt);
            }
        });
        getContentPane().add(btnGerar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 130, 32));
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 20, 20, 240));

        btnLimpar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });
        getContentPane().add(btnLimpar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 130, -1));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 650, 50, 20));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarActionPerformed
        DefaultTableModel modeloJogos = (DefaultTableModel) tableJogos.getModel();
        modeloJogos.setRowCount(0);
        geraJogos();

        scrollJogos.setVisible(true);
        tableJogos.setVisible(true);

        atualizaPlacarFinal();
        tableFinal.setVisible(true);
        scrollFinal.setVisible(true);

        ajustarTabelas();

        ordenarTabelaFinal();

    }//GEN-LAST:event_btnGerarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        DefaultTableModel modeloJogos = (DefaultTableModel) tableJogos.getModel();
        DefaultTableModel modeloFinal = (DefaultTableModel) tableFinal.getModel();

        modeloFinal.setRowCount(0);
        modeloJogos.setRowCount(0);

        tableJogos.setVisible(false);
        tableFinal.setVisible(false);
        scrollJogos.setVisible(false);
        scrollFinal.setVisible(false);

    }//GEN-LAST:event_btnLimparActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(telaBrasileirao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telaBrasileirao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telaBrasileirao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telaBrasileirao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telaBrasileirao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGerar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> listTimes;
    private javax.swing.JScrollPane scrollFinal;
    private javax.swing.JScrollPane scrollJogos;
    private javax.swing.JTable tableFinal;
    private javax.swing.JTable tableJogos;
    // End of variables declaration//GEN-END:variables
}
