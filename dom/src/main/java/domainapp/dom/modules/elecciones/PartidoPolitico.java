package domainapp.dom.modules.elecciones;

import com.google.common.util.concurrent.ListenableFutureTask;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.value.Blob;
import org.isisaddons.wicket.wickedcharts.cpt.applib.WickedChart;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.math.BigDecimal;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by German on 01/04/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_partido")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.PartidoPolitico"),
        @javax.jdo.annotations.Query(
                name = "findByNombre", language = "JDOQL",
                value = "SELECT "
                        + "FROM PartidoPolitico "
                        + "WHERE nombrePartido.startsWith(:nombrePartido)")
})
@javax.jdo.annotations.Unique(name="PARTIDOPOLITICO_UNQ", members = {"nombrePartido"})
@DomainObject(objectType = "PARTIDOPOLITICO")
public class PartidoPolitico implements Comparable<PartidoPolitico> {

     //region > Logotipo
    private Blob logoPartido;

    @javax.jdo.annotations.Persistent(defaultFetchGroup="false")
    @javax.jdo.annotations.Column(allowsNull="true")
    @MemberOrder(sequence = "1")
    public Blob getLogotipo() {
        return logoPartido;
    }

    public void setLogotipo(final Blob logo) {
        this.logoPartido = logo;
    }
    //endregion

    //region > nombrePartido (property)
    private String nombrePartido;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "False")
    @Title
    public String getNombrePartido() {
        return nombrePartido;
    }

    public void setNombrePartido(final String nombrePartido) {
        this.nombrePartido = nombrePartido;
    }
    //endregion

    //region > VotosObtenidos (action)
    @Property(notPersisted = true)
    public BigDecimal getTotalVotosOficiales(){
        Integer votos = new Integer(0);
        for (Candidato candidato : candidatos) {
            votos = votos + candidato.getVotosOficiales();
        }
        return BigDecimal.valueOf(votos);
    }

/*    @Property(notPersisted = true)
    public BigDecimal getTotalBocaDeUrna(){
        Integer votos = new Integer(0);
        for (Candidato candidato : candidatos) {
            votos = votos + candidato.getVotosBocaDeUrna();
        }
        return BigDecimal.valueOf(votos);
    }
    //endregion
    */

    //region > candidatos (collection)
    @Persistent(mappedBy = "partido")
    private SortedSet<Candidato> candidatos = new TreeSet<Candidato>();

    public SortedSet<Candidato> getCandidatos() {
        return candidatos;
    }

    public void setCandidatos(final SortedSet<Candidato> candidatos) {
        this.candidatos = candidatos;
    }

    //endregion

    //region > listasInternas (collection)

    @Persistent(mappedBy = "partido")
    private SortedSet<ListaInterna> listas = new TreeSet<ListaInterna>();

    public SortedSet<ListaInterna> getListas() {
        return listas;
    }

    public void setListas(final SortedSet<ListaInterna> listas) {
        this.listas = listas;
    }

    public PartidoPolitico agregarListas(ListaInterna lista){
        getListas().add(lista);
        return this;
    }
    public PartidoPolitico quitarListas(ListaInterna lista){
        getListas().remove(lista);
        return this;
    }
    //endregion

    private WickedChart chart;
    @PropertyLayout(hidden = Where.ANYWHERE)
    @Property(notPersisted = true)
    public WickedChart getChart() { return chart; }
    public void setChart(WickedChart chart) { this.chart = chart; }

    public WickedChart showChart() {
        return getChart();
    }

  // region compareTO
    @Override
    public int compareTo(final PartidoPolitico other) {
        return ObjectContracts.compare(this, other, "nombre");
    }
  //endregion
}
