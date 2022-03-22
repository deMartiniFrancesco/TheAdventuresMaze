import javax.swing.*;

public class Scacchi {
    private JTable scacchiera;
    private boolean controllo;

    public Scacchi(){
        scacchiera = new JTable(10,10);
        controllo = false;
    }

    public Scacchi(JTable a){
        scacchiera = a;
        controllo = false;
    }

    public JTable getScacchiera() {
        return scacchiera;
    }

    public void setScacchiera(JTable scacchiera) {
        this.scacchiera = scacchiera;
    }

    public boolean getControllo() {
        return controllo;
    }

    public void setControllo(boolean controllo) {
        this.controllo = controllo;
    }

    public void inserirePersonaggi(Albero a){
        int nr, nc;
        do {
            nr = (int) (Math.random() * 10);
            nc = (int) (Math.random() * 10);
            System.out.println(nc);
            System.out.println(nr);
            if (scacchiera == a) controllo = true;
        }while (!controllo);
        scacchiera.add();

    }

    public String spostaSu(int id, short s){
        for (int i = 0; i < scacchiera.getRowCount(); i++) {
          for (int j = 0; j < scacchiera.getColumnCount(); j++) {
            if(!scacchiera[i][j].getStato() && id == scacchiera[i][j].getNumPedina() && s == scacchiera[i][j].getColorePedina()){
              try{
                if(scacchiera[i-1][j].getStato() | scacchiera[i-1][j].getColorePedina() != s){
                  scacchiera[i-1][j].removePedina();
                  scacchiera[i-1][j].insertPedina(scacchiera[i][j].getPedina(), scacchiera[i][j].getNumPedina());
                  scacchiera[i][j].removePedina();
                  return "Ok";
                } else if(scacchiera[i-1][j].getColorePedina() == s){
                  return "Mossa non possibile colore uguale";
                }
              }catch(ArrayIndexOutOfBoundsException e){
                return "Mossa non possiblie raggiunti i limiti della scacchiera";
              }
            }
          }
        }
        return "Non trovata pedina";
      }
      public String spostaGiu(int id, short s){
        for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++) {
            if(!scacchiera[i][j].getStato() && id == scacchiera[i][j].getNumPedina() && s == scacchiera[i][j].getColorePedina()){
              try{
                if(scacchiera[i+1][j].getStato() | scacchiera[i+1][j].getColorePedina() != s){
                  scacchiera[i+1][j].removePedina();
                  scacchiera[i+1][j].insertPedina(scacchiera[i][j].getPedina(), scacchiera[i][j].getNumPedina());
                  scacchiera[i][j].removePedina();
                  return "Ok";
                } else if(scacchiera[i+1][j].getColorePedina() == s){
                  return "Mossa non possibile colore uguale";
                }
              }catch(ArrayIndexOutOfBoundsException e){
                return "Mossa non possiblie raggiunti i limiti della scacchiera";
              }
            }
          }
        }
        return "Non trovata pedina";
      }
    
      public String spostaDx(int id, short s){
        for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++) {
            if(!scacchiera[i][j].getStato() && id == scacchiera[i][j].getNumPedina() && s == scacchiera[i][j].getColorePedina()){
              try{
                if(scacchiera[i][j+1].getStato() | scacchiera[i][j+1].getColorePedina() != s){
                  scacchiera[i][j+1].removePedina();
                  scacchiera[i][j+1].insertPedina(scacchiera[i][j].getPedina(), scacchiera[i][j].getNumPedina());
                  scacchiera[i][j].removePedina();
                  return "Ok";
                } else if(scacchiera[i][j+1].getColorePedina() == s){
                  return "Mossa non possibile colore uguale";
                }
              }catch(ArrayIndexOutOfBoundsException e){
                return "Mossa non possiblie raggiunti i limiti della scacchiera";
              }
            }
          }
        }
        return "Non trovata pedina";
      }
    
      public String spostaSx(int id, short s){
        for (int i = 0; i < SIZE; i++) {
          for (int j = 0; j < SIZE; j++) {
            if(!scacchiera[i][j].getStato() && id == scacchiera[i][j].getNumPedina() && s == scacchiera[i][j].getColorePedina()){
              try{
                if(scacchiera[i][j-1].getStato() | scacchiera[i][j-1].getColorePedina() != s){
                  scacchiera[i][j-1].removePedina();
                  scacchiera[i][j-1].insertPedina(scacchiera[i][j].getPedina(), scacchiera[i][j].getNumPedina());
                  scacchiera[i][j].removePedina();
                  return "Ok";
                } else if(scacchiera[i][j-1].getColorePedina() == s){
                  return "Mossa non possibile colore uguale";
                }
              }catch(ArrayIndexOutOfBoundsException e){
                return "Mossa non possiblie raggiunti i limiti della scacchiera";
              }
            }
          }
        }
        return "Non trovata pedina";
      }
    

}
