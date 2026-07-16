package com.example.landscapedesign.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.arcore.ARSessionManager // المسار الصحيح في 2.x

@Composable
fun ArCameraPreview(
    arSessionManager: ARSessionManager,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            ArSceneView(context).apply {
                // ضبط الجلسة كما في 2.2.1
                sessionConfiguration = { _, config ->
                    config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
                    config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                    config.focusMode = Config.FocusMode.AUTO
                    config
                }
                
                // ربط الـ Session المدار بـ ARSessionManager
                onSessionCreated = { session ->
                    arSessionManager.bindSession(session)
                }
            }
        },
        modifier = modifier
    )
}
