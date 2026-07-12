package com.example.landscapedesign.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.landscapedesign.R
import com.example.landscapedesign.ar.ArCameraPreview
import com.example.landscapedesign.viewmodel.LandscapeViewModel

/**
 * STEP 1 — Real-World Area Capture & AR Boundary Definition.
 *
 * Provides a live AR camera viewfinder using Filament/Sceneview where the user
 * taps the physical ground plane to drop reference pins. The polygon enclosed
 * by these pins defines the garden boundary. Its real-world area (m²) is
 * computed live on the CPU via Shoelace formula inside the ViewModel.
 *
 * Includes a "Freeze Frame" toggle to lock the camera projection matrix,
 * transitioning the workflow from mobile AR into a steady 2.5D top-down
 * studio canvas (Step 3) without requiring continuous device aiming.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Step1AreaCaptureScreen(
    viewModel: LandscapeViewModel,
    onNext: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.step1_title)) })
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // --- Live AR Viewport / Frozen Projection Frame ---
            ArCameraPreview(
                modifier = Modifier.fillMaxSize(),
                boundaryPoints = state.gardenBoundary,
                isFrozen = state.isFrameFrozen,
                onPlaneTap = { point3D ->
                    viewModel.addBoundaryPoint(point3D)
                }
            )

            // --- Floating Overlay Controls ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Live readout card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = stringResource(R.string.captured_area_label, state.gardenAreaM2),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.points_count_label, state.gardenBoundary.size),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }

                // Control Actions Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.clearBoundary() }
                    ) {
                        Text(stringResource(R.string.btn_clear_points))
                    }

                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.toggleFrameFreeze() }
                    ) {
                        Text(
                            text = if (state.isFrameFrozen) {
                                stringResource(R.string.btn_unfreeze_frame)
                            } else {
                                stringResource(R.string.btn_freeze_frame)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Proceed Button
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.gardenBoundary.size >= 3, // Valid polygon polygon requires >= 3 nodes
                    onClick = onNext
                ) {
                    Text(stringResource(R.string.btn_confirm_area))
                }
            }
        }
    }
}