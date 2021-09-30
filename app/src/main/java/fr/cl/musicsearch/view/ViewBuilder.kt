package fr.cl.musicsearch.view

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
import javax.inject.Scope

/**
 * Builder for the {@link NewCustomRibScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class ViewBuilder(dependency: ParentComponent) : ViewBuilder<ViewView,
        ViewRouter, fr.cl.musicsearch.view.ViewBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [ViewRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [ViewRouter].
     */
    fun build(parentViewGroup: ViewGroup, input: Any): ViewRouter {
        val view = createView(parentViewGroup)
        val interactor = ViewInteractor()
        val component = DaggerViewBuilder_Component.builder()
            .parentComponent(dependency)
            .view(view)
            .input(input)
            .interactor(interactor)
            .build()
        return component.Router()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): ViewView? {
        return inflater.inflate(R.layout.rib_view, parentViewGroup, false) as ViewView
        //return null
    }

    interface ParentComponent {
        // TODO: Define dependencies required from your parent interactor here.
        fun screenStack(): ScreenStack

    }

    @dagger.Module
    abstract class Module {

        @ViewScope
        @Binds
        internal abstract fun presenter(view: ViewView): ViewInteractor.ViewPresenter


        @dagger.Module
        companion object {

            @ViewScope
            @Provides
            @JvmStatic
            internal fun router(
                screenStack: ScreenStack,
                component: Component,
                view: ViewView,
                interactor: ViewInteractor): ViewRouter {
                return ViewRouter(view, interactor, component, screenStack)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @ViewScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<ViewInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: ViewInteractor): Builder

            @BindsInstance
            fun view(view: ViewView): Builder

            @BindsInstance
            fun input(@Named("input") input: Any) : Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun Router(): ViewRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class ViewScope

}
