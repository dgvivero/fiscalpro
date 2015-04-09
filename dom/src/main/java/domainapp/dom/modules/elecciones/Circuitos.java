package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;

import java.util.List;

/**
 * Created by German on 09/04/2015.
 */
@DomainService(repositoryFor = AutoridadMesa.class)
@DomainServiceLayout(menuOrder = "60")
public class Circuitos {

//region > listAll (action)
    @Action(semantics = SemanticsOf.SAFE )
    @ActionLayout(named = "Listado")
    @MemberOrder(sequence = "1")
    public List<Circuito> listAll() {
        QueryDefault<Circuito> query = QueryDefault.create(Circuito.class,"find");
        return container.allMatches(query);
    }
//endregion
//region > listar por dni (action)
    @ActionLayout(named = "Buscar por Nro")
    @MemberOrder(sequence = "1")
    public List<Circuito> listByDNI(final@ParameterLayout(named="Nombre") Integer nro) {
        QueryDefault<Circuito> query = QueryDefault.create( Circuito.class,"findByNro","nro",nro);
        List<Circuito> circuito = container.allMatches(query);
        if(circuito.isEmpty()) {
            container.informUser("No hay ningun Circuito Electoral con numero:"+ nro);
        }
        return circuito;
    }
//endregion

    //region > create (action)
    @MemberOrder(sequence = "3")
    @ActionLayout(named = "Crear")
    public Circuito create(
            final @ParameterLayout(named = "Distrito") Distrito distrito,
            final @ParameterLayout(named = "Sección") Seccion seccion,
            final @ParameterLayout(named = "Nro") Integer nro) {

        final Circuito cr = container.newTransientInstance(Circuito.class);
        cr.setDistrito(distrito);
        cr.setSeccion(seccion);
        cr.setNroCircuito(nro);
        container.persistIfNotAlready(cr);
        return cr;
    }

//endregion



//region > injected services

    @javax.inject.Inject
    DomainObjectContainer container;

//endregion
}
