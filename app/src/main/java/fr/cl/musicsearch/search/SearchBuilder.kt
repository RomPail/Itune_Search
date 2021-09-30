package fr.cl.musicsearch.search

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import fr.cl.musicsearch.R
import fr.cl.musicsearch.core.screenstack.ScreenStack
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link NewCustomRibScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class SearchBuilder(dependency: ParentComponent) : ViewBuilder<SearchView,
        SearchRouter, SearchBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [SearchRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [SearchRouter].
     */
    fun build(parentViewGroup: ViewGroup): SearchRouter {
        val view = createView(parentViewGroup)
        val interactor = SearchInteractor()
        val component = DaggerSearchBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .interactor(interactor)
            .build()
        return component.Router()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): SearchView? {
        return inflater.inflate(R.layout.rib_search, parentViewGroup, false) as SearchView
        //return null
    }

    interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
        fun provideSearchListener(): SearchInteractor.ListenerSearch
        fun screenStack(): ScreenStack
    }

    @dagger.Module
    abstract class Module {

        @SearchScope
        @Binds
        internal abstract fun presenter(view: SearchView): SearchInteractor.SearchPresenter


        @dagger.Module
        companion object {

            @SearchScope
            @Provides
            @JvmStatic
            internal fun router(
                screenStack: ScreenStack,
                component: Component,
                view: SearchView,
                interactor: SearchInteractor): SearchRouter {
                return SearchRouter(view, interactor, component, screenStack)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @SearchScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<SearchInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: SearchInteractor): Builder

            @BindsInstance
            fun view(view: SearchView): Builder


            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun Router(): SearchRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class SearchScope

}
