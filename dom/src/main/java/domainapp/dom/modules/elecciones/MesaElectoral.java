package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;
import org.apache.isis.applib.util.TitleBuffer;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.isisaddons.wicket.gmap3.cpt.service.LocationLookupService;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

/**
 * Created by German Vivero on 31/03/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_mesa")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.MesaElectoral "),
        @javax.jdo.annotations.Query(
                name = "findByNroMesa", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.MsaElectoral "
                        + "WHERE nromesa.startsWith(:nromesa)")
})
@javax.jdo.annotations.Unique(name="MESAELECTORAL_UNQ", members = {"nroMesa"})
@DomainObject(
        objectType = "MESAELECTORAL"
)
public class MesaElectoral implements Comparable<MesaElectoral>, Locatable {

  //region Titulo de la Entidad
    public String title() {
        final TitleBuffer buf = new TitleBuffer();

        buf.append("Circuito " + circuito + " - ");
        buf.concat("Mesa " + nroMesa);
        return buf.toString();
    }
   // endregion

    //region > circuito (property)
    private Circuito circuito;

    @MemberOrder(name="General", sequence = "3")
    @Column(allowsNull = "False")
    public Circuito getCircuito() {
        return circuito;
    }

    public void setCircuito(final Circuito circuito) {
        this.circuito = circuito;
    }

    //endregion
    //region > nroMesa (property)
    private Integer nroMesa;

    @MemberOrder(name="General", sequence = "4")
    @Column(allowsNull = "False")
    @Property(regexPattern = "/^[0-9]/")
    public Integer getNroMesa() {
        return nroMesa;
    }

    public void setNroMesa(final Integer nroMesa) {
        this.nroMesa = nroMesa;
    }
    //endregion

    //region > votosBlancos (property)
    private Integer votosBlancos;

    @MemberOrder(name="Recuento de Mesa", sequence = "1")
    @Column(allowsNull = "False")
    @Property(regexPattern = "/^[0-9]/")
    public Integer getVotosBlancos() {
        return votosBlancos;
    }

    public void setVotosBlancos(final Integer votosBlancos) {
        this.votosBlancos = votosBlancos;
    }

    public Integer defaultVotosBlancos() {
        return 0;
    }
    //endregion

  //region > votosNulos (property)
    private Integer votosNulos;

    @MemberOrder(name="Recuento de Mesa", sequence = "2")
    @Column(allowsNull = "False")
    @Property(regexPattern = "/^[0-9]/")
    public Integer getVotosNulos() {
        return votosNulos;
    }

    public void setVotosNulos(final Integer votosNulos) {
        this.votosNulos = votosNulos;
    }

    public Integer defaultVotosNulos() {
        return 0;
    }
  //endregion

  //region > votosRecurridos (property)
    private Integer votosRecurridos;

    @MemberOrder(name="Recuento de Mesa", sequence = "3")
    @Column(allowsNull = "False")
    @Property(regexPattern = "/^[0-9]/")
    public Integer getVotosRecurridos() {
        return votosRecurridos;
    }

    public void setVotosRecurridos(final Integer votosRecurridos) {
        this.votosRecurridos = votosRecurridos;
    }

    public Integer defaultVotosRecurridos() {
        return 0;
    }
  //endregion

  //region > votosIdentidadInpugnada (property)
    private Integer votosIdentidadInpugnada;

    @MemberOrder(name="Recuento de Mesa", sequence = "4")
    @Column(allowsNull = "False")
    @Property(regexPattern = "/^[0-9]/")
    public Integer getVotosIdentidadInpugnada() {
        return votosIdentidadInpugnada;
    }

    public void setVotosIdentidadInpugnada(Integer votosIdentidadInpugnada) {
        this.votosIdentidadInpugnada = votosIdentidadInpugnada;
    }

    public Integer defaultVotosIdentidadInpugnada() {
        return 0;
    }
  //endregion

  //region > votantesConcurrentes (property)
    private Integer votantesConcurrentes;

    @MemberOrder(name="Recuento de Mesa", sequence = "5")
    @Column(allowsNull = "False")
    @Property(regexPattern = "/^[0-9]/")
    public Integer getVotantesConcurrentes() {
        return votantesConcurrentes;
    }

    public void setVotantesConcurrentes(final Integer votantesConcurrentes) {
        this.votantesConcurrentes = votantesConcurrentes;
    }

    public Integer defaultVotantesConcurrentes() {
        return 0;
    }
  //endregion

  //region > sobresUtilizados (property)
    private Integer sobresUtilizados;

    @MemberOrder(name="Recuento de Mesa", sequence = "6")
    @Column(allowsNull = "False")
    @Property(regexPattern = "/^[0-9]/")
    public Integer getSobresUtilizados() {
        return sobresUtilizados;
    }

    public void setSobresUtilizados(final Integer sobresUtilizados) {
        this.sobresUtilizados = sobresUtilizados;
    }

    public Integer defaultSobresUtilizados() {
        return 0;
    }
  //endregion

  //region > location (property), Locatable impl
    private Double locationLatitude;
    private Double locationLongitude;
    @Property(
            optionality = Optionality.OPTIONAL
    )
    @MemberOrder(sequence="3")
    public Location getLocation() {
        return locationLatitude != null && locationLongitude != null? new Location(locationLatitude, locationLongitude): null;
    }
    public void setLocation(final Location location) {
        locationLongitude = location != null ? location.getLongitude() : null;
        locationLatitude = location != null ? location.getLatitude() : null;
    }
    @MemberOrder(name="location", sequence="1")
    @ActionLayout(named = "Actualizar Localización")
    public MesaElectoral updateLocation(
            @ParameterLayout(named="Dirección")
            final String address) {
        final Location location = this.locationService.lookup(address);
        setLocation(location);
        return this;
    }
 //endregion


    @Override
    public int compareTo(final MesaElectoral other) {
        return ObjectContracts.compare(this, other, "nroMesa, circuito");
    }

    //Injections
    @javax.inject.Inject
    LocationLookupService locationService;
}
