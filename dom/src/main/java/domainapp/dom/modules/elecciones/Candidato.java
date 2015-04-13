package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;
import java.math.BigDecimal;


/**
 * Created by German on 01/04/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_candidato")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Candidato "),
        @javax.jdo.annotations.Query(
                name = "findByApellido", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Candidato "
                        + "WHERE apellido.startsWith(:apellido)")
})
@javax.jdo.annotations.Unique(name="CANDIDATO_UNQ", members = {"nombre", "apellido"})
@DomainObject( objectType = "CANDIDATO")
public class Candidato implements Comparable<Candidato> {

    public String title() {
        final TitleBuffer buf = new TitleBuffer();
        buf.append(apellidoCandidato + ", "+ nombreCandidato + " - "+partido.getNombrePartido());
        return buf.toString();
    }

  //region > nombre (property)
    private String nombreCandidato;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public String getNombreCandidato() {
        return nombreCandidato;
    }

    public void setNombreCandidato(final String nombre) {
        this.nombreCandidato = nombre;
    }
  //endregion

  //region > apellido (property)
    private String apellidoCandidato;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "False")
    public String getApellidoCandidato() {
        return apellidoCandidato;
    }

    public void setApellidoCandidato(final String apellido) {
        this.apellidoCandidato = apellido;
    }
  //endregion

  //region > Lista (property)
  private String lista;

    @MemberOrder(sequence = "3")
    @Column(allowsNull = "False")
    public String getListaCandidato() {
        return lista;
    }

    public void setListaCandidato(final String lista) {
        this.lista = lista;
    }
    //endregion


  //region > partido (property)
    private PartidoPolitico partido;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "False")
    public PartidoPolitico getPartido() {
        return partido;
    }

    public void setPartido(final PartidoPolitico partido) {
        this.partido = partido;
    }
   //endregion

  //region > votosBocaDeUrna (property)
    private Integer votosBocaDeUrna;

    @MemberOrder(name = "Recuento Boca de Urna",sequence = "1")
    @Column(allowsNull = "False")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(hidden =Where.ALL_TABLES)
    public Integer getVotosBocaDeUrna() {
        return votosBocaDeUrna;
    }

    public void setVotosBocaDeUrna(final Integer votosBocaDeUrna) {
        this.votosBocaDeUrna = votosBocaDeUrna;
    }
    public Integer defaultVotosBocaDeUrna() { return 0; }

    @MemberOrder(name = "Sumar un voto Boca de Urna",sequence = "2")
    public void addToBocaDeUrna(){
        votosBocaDeUrna = votosBocaDeUrna + 1;
    }

    @Property(notPersisted = true)
    public BigDecimal getTotalVotosBocaDeUrna(){return BigDecimal.valueOf(votosBocaDeUrna);}
    //endregion

    //region > votosOficiales (property)
    private Integer votosOficiales;

    @MemberOrder(name = "Recuento de Votos Oficiales",sequence = "1")
    @Column(allowsNull = "False")
    @Property(editing = Editing.DISABLED)
    @PropertyLayout(hidden = Where.ALL_TABLES)
    public Integer getVotosOficiales() {
        return votosOficiales;
    }

    public void setVotosOficiales(final Integer votosOficiales) {
        this.votosOficiales = votosOficiales;
    }
    public Integer defaultVotosOficiales() {
        return 0;
    }

    @MemberOrder(name = "Sumar votos Oficiales",sequence = "2")
    public void addToVotoOficial(final Integer votos){
        votosOficiales = votosOficiales + votos;
    }
    @Property(notPersisted = true)
    public BigDecimal getTotalVotosOficiales(){return BigDecimal.valueOf(votosOficiales);}
    //endregion



    // region compareTO
     @Override
     public int compareTo(final Candidato other) {
        return ObjectContracts.compare(this, other, "nombreCandidato, apellidoCandidato");
    }
   //endregion
}
