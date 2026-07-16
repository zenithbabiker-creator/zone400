package com.example.landscapedesign.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import com.example.landscapedesign.model.Point3D

@Composable
fun ArCameraPreview(
    modifier: Modifier = Modifier,
    boundaryPoints: List<Point3D> = emptyList(),
    isFrozen: Boolean = false,
    onPlaneTap: (Point3D) -> Unit = {}
) {
    AndroidView(
        factory = { context ->
            ArSceneView(context).apply {
                val session = engine.createSession()
                val config = Config(session).apply {
                    planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
                    updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                    focusMode = Config.FocusMode.AUTO
                }
                session.configure(config)
                this.session = session
            }
        },
        modifier = modifier
    )
}
