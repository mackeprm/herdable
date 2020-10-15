package de.mackeprm.herdable

import processing.core.PApplet

class Button(private val sketch: PApplet, private val x: Int, private val y: Int, private val label: String, val action: () -> Unit) {
    private var width = 20
    private var height = 20
    private var color = RGB(204F, 102F, 0F)

    fun isClicked(mouseX: Int, mouseY: Int): Boolean {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height
    }

    fun render() {
        sketch.stroke(color.r, color.g, color.b)
        sketch.fill(color.r, color.g, color.b)
        sketch.rect(x.toFloat(), y.toFloat(), width.toFloat(), height.toFloat())
        sketch.text(label, (x.toFloat() + width + 4), (y.toFloat() + (height * 0.5F) + 4))
    }
}