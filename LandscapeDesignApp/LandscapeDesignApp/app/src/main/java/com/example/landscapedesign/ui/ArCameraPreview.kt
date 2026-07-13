@Composable
fun ArCameraPreview(
    arSessionManager: ARSessionManager,
    onTap: (x: Float, y: Float, frame: Frame?) -> Unit,
    modifier: Modifier = Modifier
) {
    // استخدم SceneView بدلاً من ArSceneView إذا كنت تستخدم إصدار 2.x
    // وتأكد من تمرير المعاملات بشكل صحيح
    io.github.sceneview.ar.ArSceneView(
        modifier = modifier,
        onSessionConfiguration = { session, config ->
            config.planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
            config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
            config.focusMode = Config.FocusMode.AUTO
        },
        onSessionCreated = { session ->
            arSessionManager.bindSession(session)
        },
        onSessionUpdated = { session, frame ->
            // في 2.2.1، يتم تحديث الإطار تلقائياً داخل الـ Session
            arSessionManager.onFrameUpdated(session, frame)
        },
        // التعامل مع اللمس في الإصدار الجديد يتم عبر modifier.pointerInput
        // أو يمكنك الاحتفاظ بالـ setOnTouchListener داخل تحديث الـ View
    )
}
