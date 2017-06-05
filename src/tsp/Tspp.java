
package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author GERMÁN ÁBRIGO VALENZUELA
 */
public class Tspp extends javax.swing.JFrame {
            
    
    public Tspp() {
        initComponents();
        JBPermutacion.setEnabled(false); /*Código para inhabilitar botón de generarr permutación*/
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JBCargar = new javax.swing.JButton();
        JBPermutacion = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Ccosto = new javax.swing.JLabel();
        Mensaje = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JBCargar.setText("Abrir archivo...");
        JBCargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBCargarActionPerformed(evt);
            }
        });

        JBPermutacion.setText("Generar Permutacion");
        JBPermutacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JBPermutacionActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setText("Costo de solución:");

        Ccosto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Ccosto.setText("US$ - .");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Mensaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(Ccosto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 55, Short.MAX_VALUE))
                            .addComponent(JBCargar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(JBPermutacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(JBCargar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(JBPermutacion, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Ccosto, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(Mensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    BufferedReader br;
    String line;
    int[][] A = null; /*Creación de matriz con distancias entre ciudades*/
    double[] X = null; /*Creación de arreglo con cordenadas x de cada ciudad*/
    double[] Y = null; /*Creación de arreglo con cordenadas y de cada ciudad*/
    int[] C= null;
    
    /*Declaración variables de utilidad para programa*/
    int c=0; /*Contador filas de matriz de costo*/
    int d=0; /*Contador ciudades para arreglo de cordenadas*/
    int dim = 0; /*Dimensión de arreglos equivalente a numero de ciudades de archivo*/
    int ban=0; /*Contador restriccion de condición*/
    int ben=100; /*Contador restriccion de condición*/
    boolean i= false; /*Contador restriccion de condición para ciclo de matriz costo*/
    boolean g= false; /*Contador restriccion de condición para arreglos de cordenadas*/    
    
    
    private void JBCargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBCargarActionPerformed

        final JFileChooser fc = new JFileChooser(); /*Crea constructor de apertura de archivo*/
        int returnVal = fc.showOpenDialog(this); /*this porque esta asociado a esta ventana*/
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File arch = fc.getSelectedFile();
            File archivo= new File(arch.getAbsolutePath()); /*Obtención ruta de archivo*/
            try {
                br = new BufferedReader (new FileReader (archivo)); /* Codigo lectura de archivo, asignación a lector*/
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Tspp.class.getName()).log(Level.SEVERE, null, ex);
            }
            try { 
                while ((line = br.readLine())!= null) { /*Ciclo lectura de lineas de archivo*/
                    StringTokenizer myTokens = new StringTokenizer(line); /*Separación de linea de archivo en palabras o Tokens*/
                    if("EOF".equals(line)){ /*Debido a problemas de lectura en linea cuando solo es un token cuando sea EOF lo termine*/
                        break; /*codigo de corte a while*/
                    }
                    while (myTokens.hasMoreTokens()){ /*Condición que recorre todas las palabras de la linea*/
                        if(ban<2){ /*Lo utilizo para que no tire error de tokens cuando este tiene solo dos términos y pido tres ej: "Dimension: 29"*/
                            String x=myTokens.nextToken(); /*Lectura de palabra o token*/
                            if("DIMENSION:".equals(x)){ /*Comparración de la palabra buscada con el token de linea*/
                                dim=Integer.parseInt(myTokens.nextToken()); /*Conversión de palabra en numero*/
                                ban+=1;
                                break; /*Interrupciones utilizadas para que no lea mas tokens y no afecte programa, asi sale del ciclo*/
                            }
                            if("EDGE_WEIGHT_SECTION".equals(x)){
                                i = true; /*Binaria que inicia la creación de matriz con lo datos siguientes (Costos)*/
                                A= new int [dim][dim]; /*dimensionado de matriz de acuerdo a datos de ciudades en archivo*/
                                ban+=1;
                                break; /*Interrupciones utilizadas para que no lea mas tokens y no afecte programa, asi sale del ciclo*/
                            }
                        }
                        int j=0;
                        while(j<dim && c<dim && i==true){ /*Lectura de datos para matriz*/
                            A[c][j]= Integer.parseInt(myTokens.nextToken()); /*Transformación de tokens string a entero que equivale a numeros asignando a matriz*/
                            j+=1;
                        }
                        if(j==29){ /*Cada vez que termina una linea agrega a contador fila para rellenar matriz*/
                            c+=1;
                        }

                        if(c==29){
                            ben=0; /*para dar inicio a lectura de cordenadas con la linea de solo una palabra y no causar problemas en programa */
                            c+=1; /*para que no vuelva a entrar solamente*/
                            break; /*Para que luego de esto no haga el ingreso de inmediato al ciclo */
                        }

                        if(ben<1){ /*Lo utilizo para que no tire error de tokens cuando este tiene solo dos terminos y pido tres*/
                            String x=myTokens.nextToken();
                            if("DISPLAY_DATA_SECTION".equals(x)){
                                g = true; /*Apertura de siguiente estructura*/
                                X= new double [dim]; /*dimensionado de arrglo con cordenadas x*/
                                Y= new double [dim]; /*dimensionado de arrglo con cordenadas y*/
                                ben+=1; /*para que no vuelva a entrar*/
                                break;
                            }
                        }
                        if(d<dim && g==true){ /*Creación de arreglos con cordenadas*/
                            String r=myTokens.nextToken(); /*solo para que lea el primer numero*/
                            X[d]= Double.parseDouble(myTokens.nextToken()); /*Transformación de token string a double y asignación a índice de arreglo */
                            Y[d]= Double.parseDouble(myTokens.nextToken()); /*Transformación de token string a double y asignación a índice de arreglo */
                            d+=1;
                        }
                    }            
                }
                /*COMPROBAR DATOS EN MATRIZ DE COSTOS Y CORDENADAS*/
                for(int p=0;p<29;p++){
                    System.out.println ("Cordenada x de ciudad "+(p+1)+": "+X[p]);
                    System.out.println ("Cordenada y de ciudad "+(p+1)+": "+Y[p]);
                    System.out.println ("Costos de ciudad "+(p+1)+" a las otras 28: "+Arrays.toString(A[p]));
                }

                /*ARREGLO DE CIUDADES PARA CREAR PERMUTACIÓN O CAMINOS DE TSP*/
                C= new int[dim];
                for(int q=0;q<dim;q++){ /*Creación de arreglo con numeros de ciudad*/
                    C[q]=q+1;
                }
                System.out.println(Arrays.toString(C));  /*ARREGLO CON NUMERO DE CIUDAD ORDENADO*/
                Mensaje.setText("Archivo cargado: Genere permutación"); /*Actualización de mensaje en label*/
                JBPermutacion.setEnabled(true); /*Deseleccionar botón de generar permutación, una vez se abre el archivo*/

            } catch (FileNotFoundException ex) { /*Catch para en caso de error no muestre error*/
                    Logger.getLogger(Tspp.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                Logger.getLogger(Tspp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Mensaje.setText("Ningun archivo selecionado: Abrir archivo");
            Ccosto.setText("US$.-");
            JBPermutacion.setEnabled(false); /*Para desactivar botón de permutación, cuando se abrá un archivo y se quier abrir otro y se cancele */
        }
    }//GEN-LAST:event_JBCargarActionPerformed

    private void JBPermutacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JBPermutacionActionPerformed
       
    
        /*Algoritmo de Fisher-Yates para generar permutación TSP*/
        int L=C.length;
        for(int w=0;w<L;w++){
            int m=(int) (w + Math.random() * (L-w));
            int r=C[m];
            C[m]=C[w];
            C[w]=r;
        }
        System.out.println(Arrays.toString(C));  /*Permutación generada*/
        
        /*CALCULO DE COSTO DE PERMUTACIÓN*/
        int cost=0;
        for(int y=0;y<dim;y++){
            if(y==(dim-1)){ /*Para último término volver al principio*/
                cost+=A[C[y]-1][C[0]]; /*Se resta uno ya que lista parte de 0 por tanto la ciudad 29 en arreglo es indice 28*/
                System.out.println((C[y])+" y "+(C[0]));  
            }
            else{ /*Todos caminos intermedios*/
                cost+=A[C[y]-1][C[y+1]-1];
                System.out.println((C[y])+" y "+(C[y+1]));  
            }
        }
        /*Colocar monto del costo en dollares*/
        Locale locale = new Locale("en", "US");;
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale); /*Formato en función a la region*/
        System.out.println("Costo total : "+nf.format(cost));
        Mensaje.setText("");
        Ccosto.setText("US"+nf.format(cost)+".-");
    }//GEN-LAST:event_JBPermutacionActionPerformed

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tspp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Tspp().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Ccosto;
    private javax.swing.JButton JBCargar;
    private javax.swing.JButton JBPermutacion;
    private javax.swing.JLabel Mensaje;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
