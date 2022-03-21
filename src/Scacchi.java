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

}
