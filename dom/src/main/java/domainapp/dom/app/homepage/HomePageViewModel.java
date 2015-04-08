/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.app.homepage;

import domainapp.dom.modules.elecciones.*;
import domainapp.dom.modules.simple.SimpleObject;
import domainapp.dom.modules.simple.SimpleObjects;

import java.util.List;

import org.apache.isis.applib.annotation.*;

@ViewModel
@MemberGroupLayout(
        columnSpans={0,0,0,12}, left="Resultados")

public class HomePageViewModel {

    //region > title
    public String title() {
        return  " Panel de Control";
    }
    //endregion
/*
    //region > object (collection)
    @org.apache.isis.applib.annotation.HomePage
    @MemberOrder(name="Resultados",sequence="1")
    @CollectionLayout(render = RenderType.EAGERLY)
    public List<PartidoPolitico> getPartidos() {
        return partidos.listAll();
    }
    //endregion
    */

    //region > object (collection)
    @org.apache.isis.applib.annotation.HomePage
    @MemberOrder(name="Resultados", sequence="2")
    public List<Candidato> getCandidatos() {
        return candidatos.listAll();
    }
    //endregion

    //region > object (collection)
    @org.apache.isis.applib.annotation.HomePage
    @MemberOrder(sequence="99")
    public List<Fiscal> getFiscales() {
        return fiscales.listAll();
    }
    //endregion

    //region > injected services

    @javax.inject.Inject
    PartidosPoliticos partidos;

    @javax.inject.Inject
    Candidatos candidatos;

    @javax.inject.Inject
    Fiscales fiscales;



    //endregion
}
