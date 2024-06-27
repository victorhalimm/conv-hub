package edu.bluejack23_2.convhub.ui

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.flowlayout.FlowRow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import edu.bluejack23_2.convhub.R
import edu.bluejack23_2.convhub.ui.theme.ConvHubTheme

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
    var title by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var categories by remember { mutableStateOf("") }

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
                Spacer(modifier = Modifier.height(20.dp))
                Text("Step 1. Upload Images")
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

                Text("Step 2. Add Job Details")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = price,
                    onValueChange = { price = it },
                    label = { Text("Price") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(30.dp))

                Text("Step 3. Specify Job Categories")
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = categories,
                    onValueChange = { categories = it },
                    label = { Text("Categories (comma separated)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (imageUris.isNotEmpty() && title.isNotEmpty() && address.isNotEmpty() && description.isNotEmpty() && price.isNotEmpty() && categories.isNotEmpty()) {
                            val job = hashMapOf(
                                "title" to title,
                                "address" to address,
                                "description" to description,
                                "price" to price.toInt(),
                                "categories" to categories.split(",").map { it.trim() },
                                "imageUris" to imageUris.map { it.toString() },
                                "posted_at" to "",
                                "status" to "untaken",
                                "job_poster" to "null",
                                "job_taker" to "null"
                             )
                            uploadImagesAndSaveJob(imageUris, job, context)
                        } else {
                            Toast.makeText(
                                context, "Please fill in all fields and select images from gallery",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Blue)
                ) {
                    Text(text = "Upload Job")
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

fun uploadImagesAndSaveJob(imageUris: List<Uri>, job: HashMap<String, Any>, context: Context) {
    val storage = FirebaseStorage.getInstance()
    val storageReference = storage.reference
    val db = FirebaseFirestore.getInstance()

    imageUris.forEach { uri ->
        val imageReference = storageReference.child("images/" + uri.lastPathSegment)
        val uploadTask = imageReference.putFile(uri)
    }
    db.collection("job")
        .add(job)
        .addOnSuccessListener {
            Toast.makeText(
                context, "Job Upload Successful",
                Toast.LENGTH_SHORT
            ).show()
        }
        .addOnFailureListener {
            Toast.makeText(
                context, "Job Upload Failed",
                Toast.LENGTH_SHORT
            ).show()
        }
}
