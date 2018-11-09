package co.id.billyon.util.annotation

import javax.inject.Qualifier

/**
 * Create custom annotation to used with Dagger2
 * To differentiate the same instance
 * Same function with 'Named' annotation
 */
@Qualifier
@MustBeDocumented
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class Use (val value : String)