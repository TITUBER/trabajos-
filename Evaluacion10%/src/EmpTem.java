import java.time.LocalDate;
public class EmpTem extends Empleado {

    LocalDate fecIni;
    LocalDate fecFin;

    public EmpTem(String nom, String ape, String ced, int hij, LocalDate fecIni, LocalDate fecFin) {
        super(nom, ape, ced, hij);
        this.fecIni = fecIni;
        this.fecFin = fecFin;
    }

    public double sue() {
        return 18000 + hij * 1000;
    }
}