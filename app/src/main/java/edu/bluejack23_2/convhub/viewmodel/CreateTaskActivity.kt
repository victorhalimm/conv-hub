package edu.bluejack23_2.convhub.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.firebase.storage.FirebaseStorage
import edu.bluejack23_2.convhub.R
import edu.bluejack23_2.convhub.viewmodel.ui.theme.ConvHubTheme

class CreateTaskActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConvHubTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    CreateScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun CreateScreen() {

    var imageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris: List<Uri> ->
        imageUris = uris
    }

    ConvHubTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text("Create Job")
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp
                ) {
                    if(imageUris.isEmpty()){
                        Image(
                            painter = painterResource(id = R.drawable.convhub_logo_only_white),
                            contentDescription = "Placeholder Image",
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Gray, shape = RoundedCornerShape(5.dp))
                                .clickable {
                                },
                            contentScale = ContentScale.Inside
                        )
                        Image(
                            painter = painterResource(id = R.drawable.convhub_logo_only_white),
                            contentDescription = "Placeholder Image",
                            modifier = Modifier
                                .size(100.dp)
                                .background(Color.Gray, shape = RoundedCornerShape(5.dp))
                                .clickable {
                                },
                            contentScale = ContentScale.Inside
                        )
                    }else {
                        imageUris.forEach { uri ->
                            val painter: Painter = rememberAsyncImagePainter(uri)
                            Image(
                                painter = painter,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(100.dp)
                                    .width(100.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.Black,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(4.dp)
                            )
                        }
                    }
                    Button(
                        onClick = { launcher.launch("image/*") },
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Gray),
                        modifier = Modifier
                            .size(100.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (imageUris.isNotEmpty()) {
                            imageUris.forEach { uri ->
                                uploadImageToFirebase(uri, context)
                            }
                        } else {
                            Toast.makeText(
                                context, "Please select images from gallery",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Blue)
                ) {
                    Text(text = "Upload Images")
                }
            }
        }
    }
}

fun uploadImageToFirebase(uri: Uri, context: Context) {
    val storage = FirebaseStorage.getInstance()
    val storageReference = storage.reference
    val imageReference = storageReference.child("images/" + uri.lastPathSegment)

    val uploadTask = imageReference.putFile(uri)

    uploadTask.addOnSuccessListener {
        Toast.makeText(
            context, "Image Upload Successful",
            Toast.LENGTH_SHORT
        ).show()
    }.addOnFailureListener {
        Toast.makeText(
            context, "Image Upload Failed",
            Toast.LENGTH_SHORT
        ).show()
    }
}
