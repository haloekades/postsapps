package com.ekades.fcmpushnotification.lib.core.ui.foundation.color

import android.os.Build
import com.ekades.fcmpushnotification.lib.application.ApplicationProvider
import com.ekades.fcmpushnotification.lib.core.test.BaseAppTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.BaseTest
import com.ekades.fcmpushnotification.lib.core.test.viewmodels.base.shouldBe
import com.ekades.fcmpushnotification.lib.core.ui.R
import com.ekades.fcmpushnotification.lib.ui.asset.extension.asColor
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import androidx.test.core.app.ApplicationProvider as TestApp

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P], application = BaseAppTest::class)
class ColorPaletteTest : BaseTest() {
    
    override fun setup() {
        super.setup()
        mockkObject(ApplicationProvider)
        every { ApplicationProvider.context } returns TestApp.getApplicationContext()
    }
    
    @Test
    fun `every color in ColorPalette returns correct value`() {
        ColorPalette.MINE_SHAFT shouldBe R.color.mineShaft.asColor()
        ColorPalette.DARK_KNIGHT shouldBe R.color.darkKnight.asColor()
        ColorPalette.TUNDORA shouldBe R.color.tundora.asColor()
        ColorPalette.EMPEROR shouldBe R.color.emperor.asColor()
        ColorPalette.BOULDER shouldBe R.color.boulder.asColor()
        ColorPalette.DUSTY_GRAY shouldBe R.color.dustyGray.asColor()
        ColorPalette.SILVER shouldBe R.color.silver.asColor()
        ColorPalette.ALTO shouldBe R.color.alto.asColor()
        ColorPalette.MERCURY shouldBe R.color.mercury.asColor()
        ColorPalette.WILD_SAND shouldBe R.color.wildSand.asColor()
        ColorPalette.SNOW shouldBe R.color.snow.asColor()
        ColorPalette.WHITE shouldBe R.color.white.asColor()
        
        ColorPalette.BURNHAM shouldBe R.color.burnham.asColor()
        ColorPalette.KAITOKE shouldBe R.color.kaitoke.asColor()
        ColorPalette.FUN shouldBe R.color.`fun`.asColor()
        ColorPalette.SPANISH shouldBe R.color.spanish.asColor()
        ColorPalette.BRAND shouldBe R.color.brand.asColor()
        ColorPalette.DE_YORK shouldBe R.color.deYork.asColor()
        ColorPalette.FRINGY_FLOWER shouldBe R.color.fringyFlower.asColor()
        ColorPalette.HONEYDEW shouldBe R.color.honeydew.asColor()
        ColorPalette.NARVIK shouldBe R.color.narvik.asColor()
        
        ColorPalette.ESPRESSO shouldBe R.color.espresso.asColor()
        ColorPalette.CROWN_OF_THORNS shouldBe R.color.crownOfThorns.asColor()
        ColorPalette.AUBURN shouldBe R.color.auburn.asColor()
        ColorPalette.ROSE_MADDER shouldBe R.color.roseMadder.asColor()
        ColorPalette.CRAYOLA shouldBe R.color.crayola.asColor()
        ColorPalette.VIVID_TANGERINE shouldBe R.color.vividTangerine.asColor()
        ColorPalette.BABY_PINK shouldBe R.color.babyPink.asColor()
        ColorPalette.MISTY_ROSE shouldBe R.color.mistyRose.asColor()
        ColorPalette.CHARDON shouldBe R.color.chardon.asColor()
        
        ColorPalette.INDIAN_TAN shouldBe R.color.indianTan.asColor()
        ColorPalette.CEDAR_WOOD shouldBe R.color.cedarWood.asColor()
        ColorPalette.PERU_TAN shouldBe R.color.peruTan.asColor()
        ColorPalette.ROSE_OF_SHARON shouldBe R.color.roseOfSharon.asColor()
        ColorPalette.FULVOUS shouldBe R.color.fulvous.asColor()
        ColorPalette.SUNRAY shouldBe R.color.sunray.asColor()
        ColorPalette.SWEET_CORN shouldBe R.color.sweetCorn.asColor()
        ColorPalette.BANANA_MANIA shouldBe R.color.bananaMania.asColor()
        ColorPalette.COSMIC_LATTE shouldBe R.color.cosmicLatte.asColor()
    
        ColorPalette.ROYAL_DARK shouldBe R.color.royalDark.asColor()
        ColorPalette.CONGRESS_BLUE shouldBe R.color.congressBlue.asColor()
        ColorPalette.USAFA_BLUE shouldBe R.color.usafaBlue.asColor()
        ColorPalette.SCIENCE_BLUE shouldBe R.color.scienceBlue.asColor()
        ColorPalette.DODGER shouldBe R.color.dodger.asColor()
        ColorPalette.BABY_BLUES shouldBe R.color.babyBlues.asColor()
        ColorPalette.PERIWINKIE shouldBe R.color.periwinkie.asColor()
        ColorPalette.PATTENS shouldBe R.color.pattens.asColor()
        ColorPalette.ALICE_BLUE shouldBe R.color.aliceBlue.asColor()
        
        ColorPalette.RUSSIAN_VIOLET shouldBe R.color.russianViolet.asColor()
        ColorPalette.EMINENCE shouldBe R.color.eminence.asColor()
        ColorPalette.REBECCA shouldBe R.color.rebecca.asColor()
        ColorPalette.AMETHYST shouldBe R.color.amethyst.asColor()
        ColorPalette.PORTAGE shouldBe R.color.portage.asColor()
        ColorPalette.MAUVE shouldBe R.color.mauve.asColor()
        ColorPalette.FOG shouldBe R.color.fog.asColor()
        ColorPalette.LAVENDER shouldBe R.color.lavender.asColor()
        ColorPalette.MAGNOLIA shouldBe R.color.magnolia.asColor()
    
        ColorPalette.TYRIAN shouldBe R.color.tyrian.asColor()
        ColorPalette.PANSY shouldBe R.color.pansy.asColor()
        ColorPalette.FLIRT shouldBe R.color.flirt.asColor()
        ColorPalette.RED_VIOLET shouldBe R.color.redViolet.asColor()
        ColorPalette.ROSE_PINK shouldBe R.color.rosePink.asColor()
        ColorPalette.ORCHID shouldBe R.color.orchid.asColor()
        ColorPalette.COTTON_CANDY shouldBe R.color.cottonCandy.asColor()
        ColorPalette.PINK_LACE shouldBe R.color.pinkLace.asColor()
        ColorPalette.AMOUR shouldBe R.color.amour.asColor()
    }
}