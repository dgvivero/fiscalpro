package domainapp.dom.modules.elecciones;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.value.Blob;

import java.util.List;

/**
 * Created by German on 05/04/2015.
 */
@DomainService(repositoryFor = Candidato.class)
@DomainServiceLayout(menuOrder = "40")
public class Candidatos {
    //region > listAll (action)
    @Action(
            semantics = SemanticsOf.SAFE
    )
    @ActionLayout(
            bookmarking = BookmarkPolicy.AS_ROOT,
            named = "Listado de Candidatos"
    )
    @MemberOrder(sequence = "1")
    public List<Candidato> listAll() {
        return container.allInstances(Candidato.class);
    }
    //endregion

    //region > injected services

    //region > create (action)
    @MemberOrder(sequence = "2")
    @Action(invokeOn = InvokeOn.OBJECT_AND_COLLECTION)
    @ActionLayout(named = "Crear Candidato")
    public Candidato create(
            final @ParameterLayout(named="Nombre")String nombre,
            final @ParameterLayout(named="Apellido")String apellido,
            final @ParameterLayout(named ="Partido")PartidoPolitico partido) {

        final Candidato c = container.newTransientInstance(Candidato.class);
        c.setApellidoCandidato(apellido);
        c.setNombreCandidato(nombre);
        c.setPartido(partido);
        container.persistIfNotAlready(c);
        return c;
    }

    public List<PartidoPolitico> autoComplete2Create(String search) {
        return partidosPoliticos.listByName(search);
    }


    //endregion

    @javax.inject.Inject
    DomainObjectContainer container;

    @javax.inject.Inject
    PartidosPoliticos partidosPoliticos;

    //endregion
}
