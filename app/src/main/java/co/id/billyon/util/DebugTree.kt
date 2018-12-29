package co.id.billyon.util

import timber.log.Timber

class DebugTree : Timber.DebugTree() {

    override fun createStackElementTag(element: StackTraceElement): String {
        return String.format("[Class:%s] [Line:%s] [Method:%s]",
                super.createStackElementTag(element),
                element.getLineNumber(),
                element.getMethodName()
        )

    }

}