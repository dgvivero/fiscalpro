package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.util.ObjectContracts;
import org.isisaddons.wicket.gmap3.cpt.applib.Locatable;
import org.isisaddons.wicket.gmap3.cpt.applib.Location;
import org.isisaddons.wicket.gmap3.cpt.service.LocationLookupService;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by German on 13/04/2015.
 */
@javax.jdo.annotations.PersistenceCapable(identityType= IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
        column="id_establecimiento")
@javax.jdo.annotations.Version(
        strategy= VersionStrategy.VERSION_NUMBER,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Establecimiento "),
        @javax.jdo.annotations.Query(
                name = "findByNroMesa", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.modules.elecciones.Establecimiento "
                        + "WHERE nombreEstablecimiento.startsWith(:nombreEstablecimiento)")
})
@javax.jdo.annotations.Unique(name="ESTABLECIMIENTO_UNQ", members = {"nombreEstablecimiento"})
@DomainObject(objectType = "ESTABLECIMIENTO")
public class Establecimiento  implements   Comparable<Establecimiento>, Locatable {

 //region > nombreEstablecimiento (property)
    private String nombreEstablecimiento;

    @MemberOrder(sequence = "1")
    @Column(allowsNull = "True")
    public String getNombreEstablecimiento() {
        return nombreEstablecimiento;
    }

    public void setNombreEstablecimiento(final String nombreEstablecimiento) {
        this.nombreEstablecimiento = nombreEstablecimiento;
    }
 //endregion

 //region > circuito (property)
    private Circuito circuito;

    @MemberOrder(sequence = "2")
    @Column(allowsNull = "False")
    public Circuito getCircuito() {
        return circuito;
    }

    public void setCircuito(final Circuito circuito) {
        this.circuito = circuito;
    }
//endregion

 //region > mesas (collection)
    @Persistent(mappedBy = "establecimiento")
    private SortedSet<MesaElectoral> mesas = new TreeSet<MesaElectoral>();

    @MemberOrder(sequence = "1")
    public SortedSet<MesaElectoral> getMesas() {
        return mesas;
    }

    public void setMesas(final SortedSet<MesaElectoral> mesas) {
        this.mesas = mesas;
    }

    @ActionLayout(named = "Agregar Mesa")
    @Action(invokeOn= InvokeOn.COLLECTION_ONLY)
    public Establecimiento addMesa(@ParameterLayout(named = "Nro de Mesa") final Integer nroMesa,
                            @ParameterLayout(named="Dirección")
                            final String address){
        final MesaElectoral mesa = container.newTransientInstance(MesaElectoral.class);
        mesa.setEstablecimiento(this);
        mesa.setNroMesa(nroMesa);
        final Location location = this.locationService.lookup(address);
        mesa.setLocation(location);
        return this;
    }
 //endregion

//region > location (property), Locatable impl
    private Double locationLatitude;
    private Double locationLongitude;
    @Property(optionality = Optionality.OPTIONAL    )
    @MemberOrder(sequence="3")
    @Column(allowsNull = "True")
    public Location getLocation() {
            return locationLatitude != null && locationLongitude != null? new Location(locationLatitude, locationLongitude): null;
            }
    public void setLocation(final Location location) {
            locationLongitude = location != null ? location.getLongitude() : null;
            locationLatitude = location != null ? location.getLatitude() : null;
            }
    @MemberOrder(name="location", sequence="1")
    @ActionLayout(named = "Actualizar Localización")
    public Establecimiento updateLocation( @ParameterLayout(named="Dirección") final String address) {
    final Location location = this.locationService.lookup(address);
            setLocation(location);
            return this;
    }
//endregion


@Override
public int compareTo(final Establecimiento other) {
 return ObjectContracts.compare(this, other, "nombreEstablecimiento, circuito");
}

 //region > injected services
    @javax.inject.Inject
    DomainObjectContainer container;
   //Injections
    @javax.inject.Inject
    LocationLookupService locationService;
//endregion
}
