package de.mackeprm.herdable

import java.awt.geom.Point2D

interface Actor {
    fun step()
    fun render()
    fun getCurrentPosition(): Point2D.Float
}