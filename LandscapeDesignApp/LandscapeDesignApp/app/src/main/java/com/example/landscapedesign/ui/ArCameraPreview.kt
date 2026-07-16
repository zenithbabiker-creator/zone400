package com.example.landscapedesign.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.ar.core.Config
import com.google.ar.core.Frame
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.ARSessionManager // تأكد من المسار الصحيح لهذه الكلاس

@Composable
fun ArCameraPreview(
    arSessionManager: ARSessionManager,
    modifier: Modifier = Modifier
) {
    // في الإصدار 2.2.1، يتم استخدام ArSceneView كـ AndroidView
    androidx.compose.ui.viewinterop.AndroidView(
        factory = { context ->
            ArSceneView(context).apply {
                // إعداد الجلسة
                sessionConfiguration = { session, config ->
                    config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
                    config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                    config.focusMode = Config.FocusMode.AUTO
                    config
                }
                
                onSessionCreated = { session ->
                    arSessionManager.bindSession(session)
                }
                
                onSessionUpdated = { session, frame ->
                    arSessionManager.onFrameUpdated(session, frame)
                }
            }
        },
        modifier = modifier,
        update = { arSceneView ->
            // التحديثات التي تتم أثناء إعادة تركيب المكون
        }
    )
}
