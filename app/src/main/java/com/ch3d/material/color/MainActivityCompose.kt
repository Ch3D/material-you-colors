package com.ch3d.material.color

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ch3d.material.color.ui.theme.MaterialYouColorsTheme
import java.lang.Integer.min
import java.util.Locale

private val suffixList = listOf(
    "0", "10", "50", "100",
    "200", "300", "400", "500",
    "600", "700", "800", "900",
    "1000"
)

private const val RES_COLOR = "color"

@OptIn(ExperimentalFoundationApi::class, ExperimentalStdlibApi::class)
class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialYouColorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ) {
                    val tables = listOf(
                        generateColorTable("Accent 1", "accent_1_"),
                        generateColorTable("Accent 2", "accent_2_"),
                        generateColorTable("Accent 3", "accent_3_"),
                        generateColorTable("Neutral 1", "neutral_1_"),
                        generateColorTable("Neutral 2", "neutral_2_")
                    )
                    LazyColumn {
                        tables.forEach { table ->
                            stickyHeader {
                                Text(
                                    text = table.title,
                                    color = table.colors.last().color,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(8.dp),
                                    style = MaterialTheme.typography.headlineLarge,
                                    textAlign = TextAlign.Start
                                )
                            }
                            items(1) {
                                ColorGrid(table)
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    @Composable
    fun generateColorTable(
        title: String,
        prefix: String
    ) = ColorTable(
        title,
        suffixList.map {
            val colorResId = resources.getIdentifier(
                prefix + it,
                RES_COLOR,
                packageName
            )
            val color = colorResource(colorResId)
            ColorData(it, color)
        })
}

interface ColorProvider {
    val name: String
    val color: Color
}

data class ColorData(
    override val name: String,
    override val color: Color
) : ColorProvider

class ColorResData(
    override val name: String,
    private val colorRes: Int
) : ColorProvider {
    override val color: Color
        get() = Color(colorRes)
}

data class ColorTable(
    val title: String,
    val colors: List<ColorProvider>
)

@Composable
fun Cell(
    title: String,
    titleColor: Color,
    backgroundColor: Color,
    modifier: Modifier
) {
    Text(
        text = title.uppercase(Locale.ENGLISH),
        color = titleColor,
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .background(backgroundColor)
            .padding(start = 8.dp, top = 4.dp)
    )
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun ColorGrid(
    colorTable: ColorTable
) {
    val colors = colorTable.colors
    val stepSize = 4

    Column {
        for (i in 0..<colors.size step stepSize) {
            val row = i / stepSize
            val startIndex = row * stepSize
            val endIndex = min(colors.size, startIndex + stepSize)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                for (j in startIndex..<endIndex) {
                    val element = colors[j]
                    Cell(
                        title = "${element.name}\n" +
                            "#${element.color.toArgb().toHexString()}",
                        titleColor = Color.Red,
                        backgroundColor = element.color,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight()
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CellPreview() {
    MaterialYouColorsTheme {
        Cell(
            "title\nsubtitle",
            Color.White,
            Color.Red,
            Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColorGridPreview() {
    MaterialYouColorsTheme {
        ColorGrid(
            ColorTable(
                "Accent 1",
                listOf(
                    ColorData("0", colorResource(id = R.color.accent_1_0)),
                    ColorData("10", colorResource(id = R.color.accent_1_10)),
                    ColorData("50", colorResource(id = R.color.accent_1_50)),
                    ColorData("100", colorResource(id = R.color.accent_1_100)),
                    ColorData("200", colorResource(id = R.color.accent_1_200)),
                    ColorData("300", colorResource(id = R.color.accent_1_300)),
                    ColorData("400", colorResource(id = R.color.accent_1_400)),
                    ColorData("500", colorResource(id = R.color.accent_1_500)),
                    ColorData("600", colorResource(id = R.color.accent_1_600)),
                    ColorData("700", colorResource(id = R.color.accent_1_700)),
                    ColorData("800", colorResource(id = R.color.accent_1_800)),
                    ColorData("900", colorResource(id = R.color.accent_1_900)),
                    ColorData("1000", colorResource(id = R.color.accent_1_1000))
                )
            )
        )
    }
}