package com.mrecun.model


enum class Shape {
    SQUARE,
    CIRCLE,
    ROUNDED
}

class ImageShape(val shape : Shape) {

    companion object {

        val SQUARE  = ImageShape(Shape.SQUARE)
        val CIRCLE  = ImageShape(Shape.CIRCLE)
        val ROUNDED = ImageShape(Shape.ROUNDED)

        fun convert(value: Int) = when(value){
            1 -> Shape.CIRCLE
            2 -> Shape.ROUNDED
            else -> Shape.SQUARE
        }

    }

}