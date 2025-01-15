

//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

package com.eduspace.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.eduspace.ParameterSelectionActivity
import com.google.android.filament.utils.Utils
import com.eduspace.R
import com.eduspace.SimulationActivity
import com.eduspace.SuggestPostActivity
import com.eduspace.fragments.planetFragments.Earth
import com.eduspace.fragments.planetFragments.EarthMoon
import com.eduspace.fragments.planetFragments.Jupiter
import com.eduspace.fragments.planetFragments.Mars
import com.eduspace.fragments.planetFragments.MarsDeimos
import com.eduspace.fragments.planetFragments.MarsPhobos
import com.eduspace.fragments.planetFragments.Neptune
import com.eduspace.fragments.planetFragments.Saturn
import com.eduspace.fragments.planetFragments.Uranus
import com.eduspace.fragments.planetFragments.Venus
import com.eduspace.fragments.planetFragments.Mercury
import com.eduspace.fragments.planetFragments.SolarSys
import com.eduspace.fragments.planetFragments.Sun
import com.eduspace.fragments.planetFragments.jupiter.JupiterCallisto
import com.eduspace.fragments.planetFragments.jupiter.JupiterEuropa
import com.eduspace.fragments.planetFragments.jupiter.JupiterGanymede
import com.eduspace.fragments.planetFragments.jupiter.JupiterIo
import com.eduspace.fragments.planetFragments.neptune.NeptuneTriton
import com.eduspace.fragments.planetFragments.saturn.SaturnEnceladus
import com.eduspace.fragments.planetFragments.saturn.SaturnMimas
import com.eduspace.fragments.planetFragments.saturn.SaturnTethys
import com.eduspace.fragments.planetFragments.saturn.SaturnDione
import com.eduspace.fragments.planetFragments.saturn.SaturnRhea
import com.eduspace.fragments.planetFragments.saturn.SaturnTitan
import com.eduspace.fragments.planetFragments.saturn.SaturnLapetus
import com.eduspace.fragments.planetFragments.uranus.UranusAriel
import com.eduspace.fragments.planetFragments.uranus.UranusMiranda
import com.eduspace.fragments.planetFragments.uranus.UranusOberon
import com.eduspace.fragments.planetFragments.uranus.UranusTitania
import com.eduspace.fragments.planetFragments.uranus.UranusUmbriel
import com.google.android.material.navigation.NavigationView


private val planets = listOf("SolarSys","Sun","Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

private val moons = mapOf(
    "SolarSys" to listOf( "SolarSys"),
    "Sun" to listOf( "Sun"),
    "Mercury" to listOf( "Mercury"),
    "Venus" to listOf( "Venus"),
    "Earth" to listOf( "Earth","Moon"),
    "Mars" to listOf( "Mars", "Phobos", "Deimos"),
    "Jupiter" to listOf("Jupiter", "Io", "Europa", "Ganymede", "Callisto"),
    "Saturn" to listOf("Saturn","Mimas","Enceladus","Tethys","Dione", "Rhea","Titan","Lapetus"),
    "Uranus" to listOf("Uranus","Miranda", "Ariel", "Umbriel", "Titania", "Oberon"),
    "Neptune" to listOf("Neptune", "Triton")
)

private val info = mapOf(
    "SolarSys" to listOf( "Introduction",
        "The Solar System is the gravitationally bound system of the Sun and the objects that orbit it. It formed about 4.6 billion years ago when a dense region of a molecular cloud collapsed, forming the Sun and a protoplanetary disc. The Sun is a typical star that maintains a balanced equilibrium by the fusion of hydrogen into helium at its core, releasing this energy from its outer photosphere. Astronomers classify it as a G-type main-sequence star.\n" +
                "\n" +
                "The largest objects that orbit the Sun are the eight planets. In order from the Sun, they are four terrestrial planets (Mercury, Venus, Earth and Mars); two gas giants (Jupiter and Saturn); and two ice giants (Uranus and Neptune). All terrestrial planets have solid surfaces. Inversely, all giant planets do not have a definite surface, as they are mainly composed of gases and liquids. Over 99.86% of the Solar System's mass is in the Sun and nearly 90% of the remaining mass is in Jupiter and Saturn." + "\n",
        "General characteristics",
        "Astronomers sometimes divide the Solar System structure into separate regions. The inner Solar System includes Mercury, Venus, Earth, Mars, and the bodies in the asteroid belt. The outer Solar System includes Jupiter, Saturn, Uranus, Neptune, and the bodies in the Kuiper belt. Since the discovery of the Kuiper belt, the outermost parts of the Solar System are considered a distinct region consisting of the objects beyond Neptune."+"\n",
        "Orbits",
        "The planets and other large objects in orbit around the Sun lie near the plane of Earth's orbit, known as the ecliptic. Smaller icy objects such as comets frequently orbit at significantly greater angles to this plane. Most of the planets in the Solar System have secondary systems of their own, being orbited by natural satellites called moons. All of the largest natural satellites are in synchronous rotation, with one face permanently turned toward their parent. The four giant planets have planetary rings, thin discs of tiny particles that orbit them in unison.\n" +
                "\n" +
                "As a result of the formation of the Solar System, planets and most other objects orbit the Sun in the same direction that the Sun is rotating. That is, counter-clockwise, as viewed from above Earth's north pole. There are exceptions, such as Halley's Comet. Most of the larger moons orbit their planets in prograde direction, matching the direction of planetary rotation; Neptune's moon Triton is the largest to orbit in the opposite, retrograde manner. Most larger objects rotate around their own axes in the prograde direction relative to their orbit, though the rotation of Venus is retrograde." + "\n",
        "Distances and scales",
        "The radius of the Sun is 0.0047 AU (700,000 km; 400,000 mi). Thus, the Sun occupies 0.00001% (1 part in 107) of the volume of a sphere with a radius the size of Earth's orbit, whereas Earth's volume is roughly 1 millionth (10−6) that of the Sun. Jupiter, the largest planet, is 5.2 AU from the Sun and has a radius of 71,000 km (0.00047 AU; 44,000 mi), whereas the most distant planet, Neptune, is 30 AU from the Sun." +
            "\n",
        "",
        ""),
    "Sun" to listOf( "Introduction",
        " The Sun is the star at the center of the Solar System. It is a massive, nearly perfect sphere of hot plasma, heated to incandescence by nuclear fusion reactions in its core, radiating the energy from its surface mainly as visible light and infrared radiation with 10% at ultraviolet energies. It is by far the most important source of energy for life on Earth. The Sun has been an object of veneration in many cultures. It has been a central subject for astronomical research since antiquity.\n" +
                "\n" +
                "The Sun orbits the Galactic Center at a distance of 24,000 to 28,000 light-years. From Earth, it is 1 astronomical unit (1.496×108 km) or about 8 light-minutes away. Its diameter is about 1,391,400 km (864,600 mi), 109 times that of Earth. Its mass is about 330,000 times that of Earth, making up about 99.86% of the total mass of the Solar System. Roughly three-quarters of the Sun's mass consists of hydrogen (~73%); the rest is mostly helium (~25%), with much smaller quantities of heavier elements, including oxygen, carbon, neon, and iron.\n" +
                "\n" +
                "The Sun is a G-type main-sequence star (G2V), informally called a yellow dwarf, though its light is actually white. It formed approximately 4.6 billion years ago from the gravitational collapse of matter within a region of a large molecular cloud. Most of this matter gathered in the center, whereas the rest flattened into an orbiting disk that became the Solar System. The central mass became so hot and dense that it eventually initiated nuclear fusion in its core. Every second, the Sun's core fuses about 600 billion kilograms (kg) of hydrogen into helium and converts 4 billion kg of matter into energy. " + "\n",
        "Structure and fusion",
        "Illustration of the Sun's structure, in false color for contrast" + "\n",
        "Life phases",
        "The Sun today is roughly halfway through the main-sequence portion of its life. It has not changed dramatically in over four billion years and will remain fairly stable for about five billion more. However, after hydrogen fusion in its core has stopped, the Sun will undergo dramatic changes, both internally and externally." + "\n",
        " ",
        " ",
        " ",
        " "),

    "Mercury" to listOf(
        "Introduction",

        "Mercury is the first planet from the Sun and the smallest in the Solar System. In English, it is named after the ancient Roman god Mercurius (Mercury), god of commerce and communication, and the messenger of the gods. Mercury is classified as a terrestrial planet, with roughly the same surface gravity as Mars. The surface of Mercury is heavily cratered, as a result of countless impact events that have accumulated over billions of years. Its largest crater, Caloris Planitia, has a diameter of 1,550 km (960 mi), which is about one-third the diameter of the planet (4,880 km or 3,030 mi). Similarly to the Earth's Moon, Mercury's surface displays an expansive rupes system generated from thrust faults and bright ray systems formed by impact event remnants.\n" +
                "\n" +
                "Mercury's sidereal year (88.0 Earth days) and sidereal day (58.65 Earth days) are in a 3:2 ratio. This relationship is called spin–orbit resonance, and sidereal here means \"relative to the stars\". Consequently, one solar day (sunrise to sunrise) on Mercury lasts for around 176 Earth days: twice the planet's sidereal year. This means that one side of Mercury will remain in sunlight for one Mercurian year of 88 Earth days; while during the next orbit, that side will be in darkness all the time until the next sunrise after another 88 Earth days.\n" +
                "\n" +
                "Combined with its high orbital eccentricity, the planet's surface has widely varying sunlight intensity and temperature, with the equatorial regions ranging from −170 °C (−270 °F) at night to 420 °C (790 °F) during sunlight. Due to the very small axial tilt, the planet's poles are permanently shadowed. This strongly suggests that water ice could be present in the craters. Above the planet's surface is an extremely tenuous exosphere and a faint magnetic field that is strong enough to deflect solar winds. Mercury has no natural satellite." + "\n",

        "Physical characteristics",

        "Mercury is one of four terrestrial planets in the Solar System, which means it is a rocky body like Earth. It is the smallest planet in the Solar System, with an equatorial radius of 2,439.7 kilometres (1,516.0 mi). Mercury is also smaller—albeit more massive—than the largest natural satellites in the Solar System, Ganymede and Titan. Mercury consists of approximately 70% metallic and 30% silicate material." + "\n",

        "Mercury's internal structure and magnetic field",

        " ",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Venus" to listOf(
        "Introduction",

        "Venus is the second planet from the Sun. It is a terrestrial planet and is the closest in mass and size to its orbital neighbour Earth. Venus has by far the densest atmosphere of the terrestrial planets, composed mostly of carbon dioxide with a thick, global sulfuric acid cloud cover. At the surface it has a mean temperature of 737 K (464 °C; 867 °F) and a pressure 92 times that of Earth's at sea level. These extreme conditions compress carbon dioxide into a supercritical state at Venus's surface.\n" +
                "\n" +
                "Internally, Venus has a core, mantle, and crust. Venus lacks an internal dynamo, and its weakly induced magnetosphere is caused by atmospheric interactions with the solar wind. Internal heat escapes through active volcanism, resulting in resurfacing instead of plate tectonics. Venus is one of two planets in the Solar System, the other being Mercury, that have no moons. Conditions perhaps favourable for life on Venus have been identified at its cloud layers. Venus may have had liquid surface water early in its history with a habitable environment, before a runaway greenhouse effect evaporated any water and turned Venus into its present state.\n" +
                "\n" +
                "The rotation of Venus has been slowed and turned against its orbital direction (retrograde) by the currents and drag of its atmosphere. It takes 224.7 Earth days for Venus to complete an orbit around the Sun, and a Venusian solar year is just under two Venusian days long. The orbits of Venus and Earth are the closest between any two Solar System planets, approaching each other in synodic periods of 1.6 years. Venus and Earth have the lowest difference in gravitational potential of any pair of Solar System planets. This allows Venus to be the most accessible destination and a useful gravity assist waypoint for interplanetary flights from Earth." + "\n",

        "Physical characteristics",

        "Venus is one of the four terrestrial planets in the Solar System, meaning that it is a rocky body like Earth. It is similar to Earth in size and mass and is often described as Earth's \"sister\" or \"twin\". Venus is close to spherical due to its slow rotation. Venus has a diameter of 12,103.6 km (7,520.8 mi)—only 638.4 km (396.7 mi) less than Earth's—and its mass is 81.5% of Earth's, making it the third-smallest planet in the Solar System. Conditions on the Venusian surface differ radically from those on Earth because its dense atmosphere is 96.5% carbon dioxide, with most of the remaining 3.5% being nitrogen. The surface pressure is 9.3 megapascals (93 bars), and the average surface temperature is 737 K (464 °C; 867 °F), above the critical points of both major constituents and making the surface atmosphere a supercritical fluid out of mainly supercritical carbon dioxide and some supercritical nitrogen." + "\n",

        "Internal structure",

        "Without data from reflection seismology or knowledge of its moment of inertia, little direct information is available about the internal structure and geochemistry of Venus. The similarity in size and density between Venus and Earth suggests that they share a similar internal structure: a core, mantle, and crust. Like that of Earth, the Venusian core is most likely at least partially liquid because the two planets have been cooling at about the same rate, although a completely solid core cannot be ruled out. The slightly smaller size of Venus means pressures are 24% lower in its deep interior than Earth's. The predicted values for the moment of inertia based on planetary models suggest a core radius of 2,900–3,450 km. This is in line with the first observation-based estimate of 3,500 km." + "\n",

        " ",

        "The differentiated structure of Venus" + "\n",

        " ",

        " " + "\n"),

    "Earth" to listOf(
        "Introduction",

        "Earth is the third planet from the Sun and the only astronomical object known to harbor life. This is enabled by Earth being an ocean world, the only one in the Solar System sustaining liquid surface water. Almost all of Earth's water is contained in its global ocean, covering 70.8% of Earth's crust. The remaining 29.2% of Earth's crust is land, most of which is located in the form of continental landmasses within Earth's land hemisphere. Most of Earth's land is at least somewhat humid and covered by vegetation, while large sheets of ice at Earth's polar deserts retain more water than Earth's groundwater, lakes, rivers and atmospheric water combined. Earth's crust consists of slowly moving tectonic plates, which interact to produce mountain ranges, volcanoes, and earthquakes. Earth has a liquid outer core that generates a magnetosphere capable of deflecting most of the destructive solar winds and cosmic radiation.\n" +
                "\n" +
                "Earth has a dynamic atmosphere, which sustains Earth's surface conditions and protects it from most meteoroids and UV-light at entry. It has a composition of primarily nitrogen and oxygen. Water vapor is widely present in the atmosphere, forming clouds that cover most of the planet. The water vapor acts as a greenhouse gas and, together with other greenhouse gases in the atmosphere, particularly carbon dioxide (CO2), creates the conditions for both liquid surface water and water vapor to persist via the capturing of energy from the Sun's light. This process maintains the current average surface temperature of 14.76 °C (58.57 °F), at which water is liquid under normal atmospheric pressure. Differences in the amount of captured energy between geographic regions (as with the equatorial region receiving more sunlight than the polar regions) drive atmospheric and ocean currents, producing a global climate system with different climate regions, and a range of weather phenomena such as precipitation, allowing components such as nitrogen to cycle.\n" +
                "\n" +
                "Earth is rounded into an ellipsoid with a circumference of about 40,000 km. It is the densest planet in the Solar System. Of the four rocky planets, it is the largest and most massive. Earth is about eight light-minutes away from the Sun and orbits it, taking a year (about 365.25 days) to complete one revolution. Earth rotates around its own axis in slightly less than a day (in about 23 hours and 56 minutes). Earth's axis of rotation is tilted with respect to the perpendicular to its orbital plane around the Sun, producing seasons. Earth is orbited by one permanent natural satellite, the Moon, which orbits Earth at 384,400 km (1.28 light seconds) and is roughly a quarter as wide as Earth. The Moon's gravity helps stabilize Earth's axis, causes tides and gradually slows Earth's rotation. Tidal locking has made the Moon always face Earth with the same side.\n" +
                "\n" +
                "Earth, like most other bodies in the Solar System, formed 4.5 billion years ago from gas and dust in the early Solar System. During the first billion years of Earth's history, the ocean formed and then life developed within it. Life spread globally and has been altering Earth's atmosphere and surface, leading to the Great Oxidation Event two billion years ago. Humans emerged 300,000 years ago in Africa and have spread across every continent on Earth. Humans depend on Earth's biosphere and natural resources for their survival, but have increasingly impacted the planet's environment. Humanity's current impact on Earth's climate and biosphere is unsustainable, threatening the livelihood of humans and many other forms of life, and causing widespread extinctions." + "\n",

        "Size and shape",

        "Earth has a rounded shape, through hydrostatic equilibrium, with an average diameter of 12,742 kilometres (7,918 mi), making it the fifth largest planetary sized and largest terrestrial object of the Solar System.\n" +
                "\n" +
                "Due to Earth's rotation it has the shape of an ellipsoid, bulging at its Equator; its diameter is 43 kilometres (27 mi) longer there than at its poles. Earth's shape also has local topographic variations; the largest local variations, like the Mariana Trench (10,925 metres or 35,843 feet below local sea level), shortens Earth's average radius by 0.17% and Mount Everest (8,848 metres or 29,029 feet above local sea level) lengthens it by 0.14%. Since Earth's surface is farthest out from its center of mass at its equatorial bulge, the summit of the volcano Chimborazo in Ecuador (6,384.4 km or 3,967.1 mi) is its farthest point out. Parallel to the rigid land topography the ocean exhibits a more dynamic topography.\n" +
                "\n" +
                "To measure the local variation of Earth's topography, geodesy employs an idealized Earth producing a geoid shape. Such a shape is gained if the ocean is idealized, covering Earth completely and without any perturbations such as tides and winds. The result is a smooth but irregular geoid surface, providing a mean sea level (MSL) as a reference level for topographic measurements." + "\n",

        "Internal structure",

        "Earth's interior, like that of the other terrestrial planets, is divided into layers by their chemical or physical (rheological) properties. The outer layer is a chemically distinct silicate solid crust, which is underlain by a highly viscous solid mantle. The crust is separated from the mantle by the Mohorovičić discontinuity. The thickness of the crust varies from about 6 kilometres (3.7 mi) under the oceans to 30–50 km (19–31 mi) for the continents. The crust and the cold, rigid, top of the upper mantle are collectively known as the lithosphere, which is divided into independently moving tectonic plates.\n" +
                "\n" +
                "Beneath the lithosphere is the asthenosphere, a relatively low-viscosity layer on which the lithosphere rides. Important changes in crystal structure within the mantle occur at 410 and 660 km (250 and 410 mi) below the surface, spanning a transition zone that separates the upper and lower mantle. Beneath the mantle, an extremely low viscosity liquid outer core lies above a solid inner core. Earth's inner core may be rotating at a slightly higher angular velocity than the remainder of the planet, advancing by 0.1–0.5° per year, although both somewhat higher and much lower rates have also been proposed. The radius of the inner core is about one-fifth of that of Earth. The density increases with depth. Among the Solar System's planetary-sized objects, Earth is the object with the highest density." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Mars" to listOf(
        "Introduction",

        "Mars is the fourth planet from the Sun. The surface of Mars is orange-red because it is covered in iron(III) oxide dust, giving it the nickname \"the Red Planet\". Mars is among the brightest objects in Earth's sky, and its high-contrast albedo features have made it a common subject for telescope viewing. It is classified as a terrestrial planet and is the second smallest of the Solar System's planets with a diameter of 6,779 km (4,212 mi). In terms of orbital motion, a Martian solar day (sol) is equal to 24.5 hours, and a Martian solar year is equal to 1.88 Earth years (687 Earth days). Mars has two natural satellites that are small and irregular in shape: Phobos and Deimos.\n" +
                "\n" +
                "The relatively flat plains in northern parts of Mars strongly contrast with the cratered terrain in southern highlands – this terrain observation is known as the Martian dichotomy. Mars hosts many enormous extinct volcanoes (the tallest is Olympus Mons, 21.9 km or 13.6 mi tall) and one of the largest canyons in the Solar System (Valles Marineris, 4,000 km or 2,500 mi long). Geologically, the planet is fairly active with marsquakes trembling underneath the ground, dust devils sweeping across the landscape, and cirrus clouds. Carbon dioxide is substantially present in Mars's polar ice caps and thin atmosphere. During a year, there are large surface temperature swings on the surface between −78.5 °C (−109.3 °F) to 5.7 °C (42.3 °F) similar to Earth's seasons, as both planets have significant axial tilt.\n" +
                "\n" +
                "Mars was formed approximately 4.5 billion years ago. During the Noachian period (4.5 to 3.5 billion years ago), Mars's surface was marked by meteor impacts, valley formation, erosion, and the possible presence of water oceans. The Hesperian period (3.5 to 3.3–2.9 billion years ago) was dominated by widespread volcanic activity and flooding that carved immense outflow channels. The Amazonian period, which continues to the present, has been marked by the wind as a dominant influence on geological processes. Due to Mars's geological history, the possibility of past or present life on Mars remains of great scientific interest.\n" +
                "\n" +
                "Since the late 20th century, Mars has been explored by uncrewed spacecraft and rovers, with the first flyby by the Mariner 4 probe in 1965, the first orbit by the Mars 2 probe in 1971, and the first landing by the Viking 1 probe in 1976. As of 2023, there are at least 11 active probes orbiting Mars or on the Martian surface. Mars is an attractive target for future human exploration missions, though in the 2020s no such mission is planned." + "\n",

        "Physical characteristics",

        "Mars is approximately half the diameter of Earth, with a surface area only slightly less than the total area of Earth's dry land. Mars is less dense than Earth, having about 15% of Earth's volume and 11% of Earth's mass, resulting in about 38% of Earth's surface gravity. Mars is the only presently known example of a desert planet, a rocky planet with a surface akin to that of Earth's hot deserts. The red-orange appearance of the Martian surface is caused by ferric oxide, or rust. It can look like butterscotch; other common surface colors include golden, brown, tan, and greenish, depending on the minerals present." + "\n",

        "Internal structure",

        "Like Earth, Mars is differentiated into a dense metallic core overlaid by less dense rocky layers. The outermost layer is the crust, which is on average about 42–56 kilometres (26–35 mi) thick, with a minimum thickness of 6 kilometres (3.7 mi) in Isidis Planitia, and a maximum thickness of 117 kilometres (73 mi) in the southern Tharsis plateau. For comparison, Earth's crust averages 27.3 ± 4.8 km in thickness. The most abundant elements in the Martian crust are silicon, oxygen, iron, magnesium, aluminium, calcium, and potassium. Mars is confirmed to be seismically active; in 2019 it was reported that InSight had detected and recorded over 450 marsquakes and related events.\n" +
                "\n" +
                "Beneath the crust is a silicate mantle responsible for many of the tectonic and volcanic features on the planet's surface. The upper Martian mantle is a low-velocity zone, where the velocity of seismic waves is lower than surrounding depth intervals. The mantle appears to be rigid down to the depth of about 250 km, giving Mars a very thick lithosphere compared to Earth. Below this the mantle gradually becomes more ductile, and the seismic wave velocity starts to grow again. The Martian mantle does not appear to have a thermally insulating layer analogous to Earth's lower mantle; instead, below 1050 km in depth, it becomes mineralogically similar to Earth's transition zone. At the bottom of the mantle lies a basal liquid silicate layer approximately 150–180 km thick.\n" +
                "\n" +
                "Mars's iron and nickel core is completely molten, with no solid inner core. It is around half of Mars's radius, approximately 1650–1675 km, and is enriched in light elements such as sulfur, oxygen, carbon, and hydrogen." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Jupiter" to listOf(
        "Introduction",

        "Jupiter is the fifth planet from the Sun and the largest in the Solar System. It is a gas giant with a mass more than 2.5 times that of all the other planets in the Solar System combined and slightly less than one-thousandth the mass of the Sun. Its diameter is eleven times that of Earth, and a tenth that of the Sun. Jupiter orbits the Sun at a distance of 5.20 AU (778.5 Gm), with an orbital period of 11.86 years. It is the third brightest natural object in the Earth's night sky, after the Moon and Venus, and has been observed since prehistoric times. Its name derives from that of Jupiter, the chief deity of ancient Roman religion.\n" +
                "\n" +
                "Jupiter was the first of the Sun's planets to form, and its inward migration during the primordial phase of the Solar System affected much of the formation history of the other planets. Hydrogen constitutes 90% of Jupiter's volume, followed by helium, which forms 25% of its mass and 10% of its volume. The ongoing contraction of Jupiter's interior generates more heat than the planet receives from the Sun. Its internal structure is believed to consist of an outer mantle of fluid metallic hydrogen and a diffuse inner core of denser material. Because of its rapid rate of rotation, one turn in ten hours, Jupiter is an oblate spheroid; it has a slight but noticeable bulge around the equator. The outer atmosphere is divided into a series of latitudinal bands, with turbulence and storms along their interacting boundaries; the most obvious result of this is the Great Red Spot, a giant storm that has been recorded since 1831.\n" +
                "\n" +
                "Jupiter is surrounded by a faint system of planetary rings and has a powerful magnetosphere, the second-largest contiguous structure in the Solar System (after the heliosphere). Jupiter has 95 known moons and probably many more; the four largest moons were discovered by Galileo Galilei in 1610: Io, Europa, Ganymede, and Callisto. Ganymede, the largest of the four, is larger than the planet Mercury.\n" +
                "\n" +
                "Since 1973, Jupiter has been visited by nine robotic probes: seven flybys and two dedicated orbiters, with two more en route." + "\n",

        "Physical characteristics",

        "Jupiter is a gas giant, meaning its chemical composition is primarily hydrogen and helium. These materials are classified as gasses in planetary geology, a term that does not denote the state of matter. It is the largest planet in the Solar System, with a diameter of 142,984 km (88,846 mi) at its equator, giving it a volume 1,321 times that of the Earth. Its average density, 1.326 g/cm3, is lower than those of the four terrestrial planets." + "\n",

        "Size and mass",

        "Jupiter is about ten times larger than Earth (11.209 R\uD83D\uDF28) and smaller than the Sun (0.10276 R☉). Jupiter's mass is 318 times that of Earth; 2.5 times that of all the other planets in the Solar System combined. It is so massive that its barycentre with the Sun lies above the Sun's surface at 1.068 solar radii from the Sun's centre. Jupiter's radius is about one tenth the radius of the Sun, and its mass is one thousandth the mass of the Sun, as the densities of the two bodies are similar. A \"Jupiter mass\" (MJ or MJup) is used as a unit to describe masses of other objects, particularly extrasolar planets and brown dwarfs. For example, the extrasolar planet HD 209458 b has a mass of 0.69 MJ, while the brown dwarf Gliese 229 b has a mass of 60.4 MJ.\n" +
                "\n" +
                "Theoretical models indicate that if Jupiter had over 40% more mass, the interior would be so compressed that its volume would decrease despite the increasing amount of matter. For smaller changes in its mass, the radius would not change appreciably. As a result, Jupiter is thought to have about as large a diameter as a planet of its composition and evolutionary history can achieve. The process of further shrinkage with increasing mass would continue until appreciable stellar ignition was achieved. Although Jupiter would need to be about 75 times more massive to fuse hydrogen and become a star, its diameter is sufficient as the smallest red dwarf may be slightly larger in radius than Saturn.\n" +
                "\n" +
                "Jupiter radiates more heat than it receives through solar radiation, due to the Kelvin–Helmholtz mechanism within its contracting interior.This process causes Jupiter to shrink by about 1 mm (0.039 in) per year. At the time of its formation, Jupiter was hotter and was about twice its current diameter." + "\n",

        "Internal structure",

        "Before the early 21st century, most scientists proposed one of two scenarios for the formation of Jupiter. If the planet accreted first as a solid body, it would consist of a dense core, a surrounding layer of fluid metallic hydrogen (with some helium) extending outward to about 80% of the radius of the planet, and an outer atmosphere consisting primarily of molecular hydrogen. Alternatively, if the planet collapsed directly from the gaseous protoplanetary disk, it was expected to completely lack a core, consisting instead of a denser and denser fluid (predominantly molecular and metallic hydrogen) all the way to the centre. Data from the Juno mission showed that Jupiter has a diffuse core that mixes into its mantle, extending for 30–50% of the planet's radius, and comprising heavy elements with a combined mass 7–25 times the Earth. This mixing process could have arisen during formation, while the planet accreted solids and gases from the surrounding nebula. Alternatively, it could have been caused by an impact from a planet of about ten Earth masses a few million years after Jupiter's formation, which would have disrupted an originally compact Jovian core.\n" +
                "\n" +
                "Outside the layer of metallic hydrogen lies a transparent interior atmosphere of hydrogen. At this depth, the pressure and temperature are above molecular hydrogen's critical pressure of 1.3 MPa and critical temperature of 33 K (−240.2 °C; −400.3 °F). In this state, there are no distinct liquid and gas phases—hydrogen is said to be in a supercritical fluid state. The hydrogen and helium gas extending downward from the cloud layer gradually transitions to a liquid in deeper layers, possibly resembling something akin to an ocean of liquid hydrogen and other supercritical fluids. Physically, the gas gradually becomes hotter and denser as depth increases.\n" +
                "\n" +
                "Rain-like droplets of helium and neon precipitate downward through the lower atmosphere, depleting the abundance of these elements in the upper atmosphere. Calculations suggest that helium drops separate from metallic hydrogen at a radius of 60,000 km (37,000 mi) (11,000 km (6,800 mi) below the cloud tops) and merge again at 50,000 km (31,000 mi) (22,000 km (14,000 mi) beneath the clouds). Rainfalls of diamonds have been suggested to occur, as well as on Saturn and the ice giants Uranus and Neptune.\n" +
                "\n" +
                "The temperature and pressure inside Jupiter increase steadily inward as the heat of planetary formation can only escape by convection. At a surface depth where the atmospheric pressure level is 1 bar (0.10 MPa), the temperature is around 165 K (−108 °C; −163 °F). The region where supercritical hydrogen changes gradually from a molecular fluid to a metallic fluid spans pressure ranges of 50–400 GPa with temperatures of 5,000–8,400 K (4,730–8,130 °C; 8,540–14,660 °F), respectively. The temperature of Jupiter's diluted core is estimated to be 20,000 K (19,700 °C; 35,500 °F) with a pressure of around 4,000 GPa." + "\n",

        " ",

        " " + "\n"),

    "Saturn" to listOf(
        "Introduction",

        "Saturn is the sixth planet from the Sun and the second largest in the Solar System, after Jupiter. It is a gas giant, with an average radius of about nine times that of Earth. It has an eighth the average density of Earth, but is over 95 times more massive. Even though Saturn is almost as big as Jupiter, Saturn has less than a third the mass of Jupiter. Saturn orbits the Sun at a distance of 9.59 AU (1,434 million km), with an orbital period of 29.45 years.\n" +
                "\n" +
                "Saturn's interior is thought to be composed of a rocky core, surrounded by a deep layer of metallic hydrogen, an intermediate layer of liquid hydrogen and liquid helium, and an outer layer of gas. Saturn has a pale yellow hue, due to ammonia crystals in its upper atmosphere. An electrical current in the metallic hydrogen layer is thought to give rise to Saturn's planetary magnetic field, which is weaker than Earth's, but has a magnetic moment 580 times that of Earth because of Saturn's greater size. Saturn's magnetic field strength is about a twentieth that of Jupiter. The outer atmosphere is generally bland and lacking in contrast, although long-lived features can appear. Wind speeds on Saturn can reach 1,800 kilometres per hour (1,100 miles per hour).\n" +
                "\n" +
                "The planet has a bright and extensive system of rings, composed mainly of ice particles, with a smaller amount of rocky debris and dust. At least 146 moon orbit the planet, of which 63 are officially named; these do not include the hundreds of moonlets in the rings. Titan, Saturn's largest moon and the second largest in the Solar System, is larger (and less massive) than the planet Mercury and is the only moon in the Solar System that has a substantial atmosphere." + "\n",

        "Physical characteristics",

        "Saturn is a gas giant, composed predominantly of hydrogen and helium. It lacks a definite surface, though it is likely to have a solid core. The planet's rotation makes it an oblate spheroid—a ball flattened at the poles and bulging at the equator. Its equatorial radius is more than 10% larger than the polar radius: 60,268 km versus 54,364 km (37,449 mi versus 33,780 mi). Jupiter, Uranus, and Neptune, the other giant planets in the Solar System, are less oblate. The combination of the bulge and the rotation rate means that the effective surface gravity along the equator, 8.96 m/s2, is 74% of what it is at the poles and is lower than the surface gravity of Earth. However, the equatorial escape velocity, nearly 36 km/s, is much higher than that of Earth.\n" +
                "\n" +
                "Saturn is the only planet of the Solar System that is less dense than water—about 30% less. Although Saturn's core is considerably denser than water, the average specific density of the planet is 0.69 g/cm3, because of the atmosphere. Jupiter has 318 times Earth's mass, and Saturn is 95 times Earth's mass. Together, Jupiter and Saturn hold 92% of the total planetary mass in the Solar System." + "\n",

        "Internal structure",

        "Despite consisting mostly of hydrogen and helium, most of Saturn's mass is not in the gas phase, because hydrogen becomes a non-ideal liquid when the density is above 0.01 g/cm3, which is reached at a radius containing 99.9% of Saturn's mass. The temperature, pressure, and density inside Saturn all rise steadily toward the core, which causes hydrogen to be a metal in the deeper layers.\n" +
                "\n" +
                "Standard planetary models suggest that the interior of Saturn is similar to that of Jupiter, having a small rocky core surrounded by hydrogen and helium, with trace amounts of various volatiles. Analysis of the distortion shows that Saturn is substantially more centrally condensed than Jupiter and therefore contains much more material denser than hydrogen near its center. Saturn's central regions are about 50% hydrogen by mass, and Jupiter's are about 67% hydrogen.\n" +
                "\n" +
                "This core is similar in composition to Earth, but is more dense. The examination of Saturn's gravitational moment, in combination with physical models of the interior, has allowed constraints to be placed on the mass of Saturn's core. In 2004, scientists estimated that the core must be 9–22 times the mass of Earth, which corresponds to a diameter of about 25,000 km (16,000 mi). However, measurements of Saturn's rings suggest a much more diffuse core, with a mass equal to about 17 Earths and a radius equal to about 60% of Saturn's entire radius. This is surrounded by a thicker, liquid metallic hydrogen layer, followed by a liquid layer of helium-saturated molecular hydrogen, which gradually transitions to a gas as altitude increases. The outermost layer spans about 1,000 km (620 mi) and consists of gas.\n" +
                "\n" +
                "Saturn has a hot interior, reaching 11,700 °C (21,100 °F) at its core, and radiates 2.5 times more energy into space than it receives from the Sun. Jupiter's thermal energy is generated by the Kelvin–Helmholtz mechanism of slow gravitational compression; but such a process alone may not be sufficient to explain heat production for Saturn, because it is less massive. An alternative or additional mechanism may be the generation of heat through the \"raining out\" of droplets of helium deep in Saturn's interior. As the droplets descend through the lower-density hydrogen, the process releases heat by friction and leaves Saturn's outer layers depleted of helium. These descending droplets may have accumulated into a helium shell surrounding the core. Rainfalls of diamonds have been suggested to occur within Saturn, as well as in Jupiter and ice giants Uranus and Neptune." + "\n",

        "Natural satellites",

        "Saturn has 146 known moons, 63 of which have formal names. It is estimated that there are another 100±30 outer irregular moons larger than 3 km (2 mi) in diameter. In addition, there is evidence of dozens to hundreds of moonlets with diameters of 40–500 meters in Saturn's rings, which are not considered to be true moons. Titan, the largest moon, comprises more than 90% of the mass in orbit around Saturn, including the rings. Saturn's second-largest moon, Rhea, may have a tenuous ring system of its own, along with a tenuous atmosphere.\n" +
                "\n" +
                "Many of the other moons are small: 131 are less than 50 km in diameter. Traditionally, most of Saturn's moons have been named after Titans of Greek mythology. Titan is the only satellite in the Solar System with a major atmosphere, in which a complex organic chemistry occurs. It is the only satellite with hydrocarbon lakes.\n" +
                "\n" +
                "On 6 June 2013, scientists at the IAA-CSIC reported the detection of polycyclic aromatic hydrocarbons in the upper atmosphere of Titan, a possible precursor for life. On 23 June 2014, NASA claimed to have strong evidence that nitrogen in the atmosphere of Titan came from materials in the Oort cloud, associated with comets, and not from the materials that formed Saturn in earlier times.\n" +
                "\n" +
                "Saturn's moon Enceladus, which seems similar in chemical makeup to comets, has often been regarded as a potential habitat for microbial life. Evidence of this possibility includes the satellite's salt-rich particles having an \"ocean-like\" composition that indicates most of Enceladus's expelled ice comes from the evaporation of liquid salt water. A 2015 flyby by Cassini through a plume on Enceladus found most of the ingredients to sustain life forms that live by methanogenesis.\n" +
                "\n" +
                "In April 2014, NASA scientists reported the possible beginning of a new moon within the A Ring, which was imaged by Cassini on 15 April 2013." + "\n",

        " ",

        " " + "\n"),

    "Uranus" to listOf(
        "Introduction",

        "Uranus is the seventh planet from the Sun. It is a gaseous cyan-coloured ice giant. Most of the planet is made of water, ammonia, and methane in a supercritical phase of matter, which astronomy calls \"ice\" or volatiles. The planet's atmosphere has a complex layered cloud structure and has the lowest minimum temperature (49 K (−224 °C; −371 °F)) of all the Solar System's planets. It has a marked axial tilt of 82.23° with a retrograde rotation period of 17 hours and 14 minutes. This means that in an 84-Earth-year orbital period around the Sun, its poles get around 42 years of continuous sunlight, followed by 42 years of continuous darkness.\n" +
                "\n" +
                "Uranus has the third-largest diameter and fourth-largest mass among the Solar System's planets. Based on current models, inside its volatile mantle layer is a rocky core, and surrounding it is a thick hydrogen and helium atmosphere. Trace amounts of hydrocarbons (thought to be produced via hydrolysis) and carbon monoxide along with carbon dioxide (thought to have been originated from comets) have been detected in the upper atmosphere. There are many unexplained climate phenomena in Uranus's atmosphere, such as its peak wind speed of 900 km/h (560 mph), variations in its polar cap, and its erratic cloud formation. The planet also has very low internal heat compared to other giant planets, the cause of which remains unclear.\n" +
                "\n" +
                "Like the other giant planets, Uranus has a ring system, a magnetosphere, and many natural satellites. The extremely dark ring system reflects only about 2% of the incoming light. Uranus's 28 natural satellites include 18 known regular moons, of which 13 are small inner moons. Further out are the larger five major moons of the planet: Miranda, Ariel, Umbriel, Titania, and Oberon. Orbiting at a much greater distance from Uranus are the ten known irregular moons. The planet's magnetosphere is highly asymmetric and has many charged particles, which may be the cause of the darkening of its rings and moons." + "\n",

        "Size and mass",

        "Uranus's mass is roughly 14.5 times that of Earth, making it the least massive of the giant planets. Its diameter is slightly larger than Neptune's at roughly four times that of Earth. A resulting density of 1.27 g/cm3 makes Uranus the second least dense planet, after Saturn. This value indicates that it is made primarily of various ices, such as water, ammonia, and methane. The total mass of ice in Uranus's interior is not precisely known, because different figures emerge depending on the model chosen; it must be between 9.3 and 13.5 Earth masses. Hydrogen and helium constitute only a small part of the total, with between 0.5 and 1.5 Earth masses. The remainder of the non-ice mass (0.5 to 3.7 Earth masses) is accounted for by rocky material." + "\n",

        "Internal structure",

        "The standard model of Uranus's structure is that it consists of three layers: a rocky (silicate/iron–nickel) core in the centre, an icy mantle in the middle, and an outer gaseous hydrogen/helium envelope. The core is relatively small, with a mass of only 0.55 Earth masses and a radius less than 20% of the planet; the mantle comprises its bulk, with around 13.4 Earth masses, and the upper atmosphere is relatively insubstantial, weighing about 0.5 Earth masses and extending for the last 20% of Uranus's radius. Uranus's core density is around 9 g/cm3, with a pressure in the centre of 8 million bars (800 GPa) and a temperature of about 5000 K. The ice mantle is not in fact composed of ice in the conventional sense, but of a hot and dense fluid consisting of water, ammonia and other volatiles. This fluid, which has a high electrical conductivity, is sometimes called a water–ammonia ocean.\n"
                + "\n" +
        "The extreme pressure and temperature deep within Uranus may break up the methane molecules, with the carbon atoms condensing into crystals of diamond that rain down through the mantle like hailstones. This phenomenon is similar to diamond rains that are theorised by scientists to exist on Jupiter, Saturn, and Neptune. Very-high-pressure experiments at the Lawrence Livermore National Laboratory suggest that an ocean of metallic liquid carbon, perhaps with floating solid 'diamond-bergs', may comprise the base of the mantle.\n" +
                "\n" +
                "The bulk compositions of Uranus and Neptune are different from those of Jupiter and Saturn, with ice dominating over gases, hence justifying their separate classification as ice giants. There may be a layer of ionic water where the water molecules break down into a soup of hydrogen and oxygen ions, and deeper down superionic water in which the oxygen crystallises but the hydrogen ions move freely within the oxygen lattice." +"\n",

        "Moons",

        "Uranus has 28 known natural satellites. The names of these satellites are chosen from characters in the works of Shakespeare and Alexander Pope. The five main satellites are Miranda, Ariel, Umbriel, Titania, and Oberon. The Uranian satellite system is the least massive among those of the giant planets; the combined mass of the five major satellites would be less than half that of Triton (largest moon of Neptune) alone. The largest of Uranus's satellites, Titania, has a radius of only 788.9 km (490.2 mi), or less than half that of the Moon, but slightly more than Rhea, the second-largest satellite of Saturn, making Titania the eighth-largest moon in the Solar System. Uranus's satellites have relatively low albedos; ranging from 0.20 for Umbriel to 0.35 for Ariel (in green light). They are ice–rock conglomerates composed of roughly 50% ice and 50% rock. The ice may include ammonia and carbon dioxide." + "\n",

        " ",

        " " + "\n"),

    "Neptune" to listOf(
        "Introduction",

        "Neptune is the eighth and farthest known planet from the Sun. It is the fourth-largest planet in the Solar System by diameter, the third-most-massive planet, and the densest giant planet. It is 17 times the mass of Earth. Compared to its fellow ice giant Uranus, Neptune is slightly more massive, but denser and smaller. Being composed primarily of gases and liquids, it has no well-defined solid surface, and orbits the Sun once every 164.8 years at an orbital distance of 30.1 astronomical units (4.5 billion kilometres; 2.8 billion miles). It is named after the Roman god of the sea and has the astronomical symbol ♆, representing Neptune's trident.\n" +
                "\n" +
                "Neptune is not visible to the unaided eye and is the only planet in the Solar System that was found from mathematical predictions derived from indirect observations rather than being initially observed by direct empirical observation, when unexpected changes in the orbit of Uranus led Alexis Bouvard to hypothesise that its orbit was subject to gravitational perturbation by an unknown planet. After Bouvard's death, the position of Neptune was predicted from his observations, independently, by John Couch Adams and Urbain Le Verrier. Neptune was subsequently directly observed with a telescope on 23 September 1846 by Johann Gottfried Galle within a degree of the position predicted by Le Verrier. Its largest moon, Triton, was discovered shortly thereafter, though none of the planet's remaining moons were located telescopically until the 20th century." + "\n",

        "Physical characteristics",

        "Neptune's mass of 1.0243×1026 kg is intermediate between Earth and the larger gas giants: it is 17 times that of Earth but just 1/19th that of Jupiter. Its gravity at 1 bar is 11.15 m/s2, 1.14 times the surface gravity of Earth, and surpassed only by Jupiter. Neptune's equatorial radius of 24,764 km is nearly four times that of Earth. Neptune, like Uranus, is an ice giant, a subclass of giant planet, because they are smaller and have higher concentrations of volatiles than Jupiter and Saturn. In the search for exoplanets, Neptune has been used as a metonym: discovered bodies of similar mass are often referred to as \"Neptunes\", just as scientists refer to various extrasolar bodies as \"Jupiters\"." + "\n",

        "Internal structure",

        "Neptune's internal structure resembles that of Uranus. Its atmosphere forms about 5 to 10% of its mass and extends perhaps 10 to 20% of the way towards the core. Pressure in the atmosphere reaches about 10 GPa, or about 105 atmospheres. Increasing concentrations of methane, ammonia and water are found in the lower regions of the atmosphere." + "\n" + "The mantle is equivalent to 10 to 15 Earth masses and is rich in water, ammonia and methane. As is customary in planetary science, this mixture is called icy even though it is a hot, dense supercritical fluid. This fluid, which has a high electrical conductivity, is sometimes called a water–ammonia ocean. The mantle may consist of a layer of ionic water in which the water molecules break down into a soup of hydrogen and oxygen ions, and deeper down superionic water in which the oxygen crystallizes but the hydrogen ions float around freely within the oxygen lattice. At a depth of 7,000 km, the conditions may be such that methane decomposes into diamond crystals that rain downwards like hailstones. Scientists believe that this kind of diamond rain occurs on Jupiter, Saturn, and Uranus. Very-high-pressure experiments at Lawrence Livermore National Laboratory suggest that the top of the mantle may be an ocean of liquid carbon with floating solid 'diamonds'.\n" +
                "\n" +
                "The core of Neptune is likely composed of iron, nickel and silicates, with an interior model giving a mass about 1.2x that of Earth. The pressure at the centre is 7 Mbar (700 GPa), about twice as high as that at the centre of Earth, and the temperature may be 5,400 K (5,100 °C; 9,300 °F)."  + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Moon" to listOf(
        "Introduction",

        "The Moon is Earth's only natural satellite. It orbits at an average distance of 384,400 km (238,900 mi), about 30 times the diameter of Earth. Tidal forces between Earth and the Moon have synchronized the Moon's orbital period (lunar month) with its rotation period (lunar day) at 29.5 Earth days, causing the same side of the Moon to always face Earth. The Moon's gravitational pull—and, to a lesser extent, the Sun's—are the main drivers of Earth's tides." + "\n",

        "Size and mass",

        "The Moon's diameter is about 3,500 km, more than a quarter of Earth's, with the face of the Moon comparable to the width of either Mainland Australia, Europe or the Contiguous United States (which excludes Alaska, etc.). The whole surface area of the Moon is about 38 million square kilometers, comparable to North and South America combined, the combined American landmass having an area (excluding all islands) of 37.7 million square kilometers.\n" +
                "\n" +
                "The Moon's mass is 1/81 of Earth's, being the second densest among the planetary moons, and having the second highest surface gravity, after Io, at 0.1654 g and an escape velocity of 2.38 km/s (8600 km/h; 5300 mph)." + "\n",

        "Structure",

        "The Moon is a differentiated body that was initially in hydrostatic equilibrium but has since departed from this condition. It has a geochemically distinct crust, mantle, and core. The Moon has a solid iron-rich inner core with a radius possibly as small as 240 kilometres (150 mi) and a fluid outer core primarily made of liquid iron with a radius of roughly 300 kilometres (190 mi). Around the core is a partially molten boundary layer with a radius of about 500 kilometres (310 mi). This structure is thought to have developed through the fractional crystallization of a global magma ocean shortly after the Moon's formation 4.5 billion years ago.\n" +
                "\n" +
                "Crystallization of this magma ocean would have created a mafic mantle from the precipitation and sinking of the minerals olivine, clinopyroxene, and orthopyroxene; after about three-quarters of the magma ocean had crystallized, lower-density plagioclase minerals could form and float into a crust atop. The final liquids to crystallize would have been initially sandwiched between the crust and mantle, with a high abundance of incompatible and heat-producing elements. Consistent with this perspective, geochemical mapping made from orbit suggests a crust of mostly anorthosite. The Moon rock samples of the flood lavas that erupted onto the surface from partial melting in the mantle confirm the mafic mantle composition, which is more iron-rich than that of Earth. The crust is on average about 50 kilometres (31 mi) thick.\n" +
                "\n" +
                "The Moon is the second-densest satellite in the Solar System, after Io. However, the inner core of the Moon is small, with a radius of about 350 kilometres (220 mi) or less, around 20% of the radius of the Moon. Its composition is not well understood, but is probably metallic iron alloyed with a small amount of sulfur and nickel; analyzes of the Moon's time-variable rotation suggest that it is at least partly molten. The pressure at the lunar core is estimated to be 5 GPa (49,000 atm)." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Phobos" to listOf(
        "Introduction",

        "Phobos is the innermost and larger of the two natural satellites of Mars, the other being Deimos. The two moons were discovered in 1877 by American astronomer Asaph Hall. Phobos is named after the Greek god of fear and panic, who is the son of Ares (Mars) and twin brother of Deimos.\n" +
                "\n" +
                "Phobos is a small, irregularly shaped object with a mean radius of 11 km (7 mi). It orbits 6,000 km (3,700 mi) from the Martian surface, closer to its primary body than any other known natural satellite to a planet. It orbits Mars much faster than Mars rotates and completes an orbit in just 7 hours and 39 minutes. As a result, from the surface of Mars it appears to rise in the west, move across the sky in 4 hours and 15 minutes or less, and set in the east, twice each Martian day. Phobos is one of the least reflective bodies in the Solar System, with an albedo of 0.071. Surface temperatures range from about −4 °C (25 °F) on the sunlit side to −112 °C (−170 °F) on the shadowed side. The notable surface feature is the large impact crater, Stickney, which takes up a substantial proportion of the moon's surface. The surface is also marked by many grooves, and there are numerous theories as to how these grooves were formed.\n" +
                "\n" +
                "Images and models indicate that Phobos may be a rubble pile held together by a thin crust that is being torn apart by tidal interactions. Phobos gets closer to Mars by about 2 centimetres (0.79 in) per yea" + "\n",

        "Physical characteristics",

        "Phobos has dimensions of 26 by 23 by 18 kilometres (16 mi × 14 mi × 11 mi), and retains too little mass to be rounded under its own gravity. Phobos does not have an atmosphere due to its low mass and low gravity. It is one of the least reflective bodies in the Solar System, with an albedo of about 0.071. Infrared spectra show that it has carbon-rich material found in carbonaceous chondrites, and its composition shows similarities to that of Mars' surface. Phobos' density is too low to be solid rock, and it is known to have significant porosity. These results led to the suggestion that Phobos might contain a substantial reservoir of ice. Spectral observations indicate that the surface regolith layer lacks hydration, but ice below the regolith is not ruled out. Surface temperatures range from about −4 °C (25 °F) on the sunlit side to −112 °C (−170 °F) on the shadowed side." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Deimos" to listOf(
        "Introduction",

        "Deimos is the smaller and outer of the two natural satellites of Mars, the other being Phobos. Deimos has a mean radius of 6.2 km (3.9 mi) and takes 30.3 hours to orbit Mars. Deimos is 23,460 km (14,580 mi) from Mars, much farther than Mars's other moon, Phobos. It is named after Deimos, the Ancient Greek god and personification of dread and terror." + "\n",

        "Physical characteristics",

        "Deimos is a gray-colored body. Like most bodies of its size, Deimos is highly non-spherical with triaxial dimensions of 16.1 km × 11.8 km × 10.2 km (10.0 mi × 7.3 mi × 6.3 mi), corresponding to a mean diameter of 12.5 km (7.8 mi) which makes it about 57% the size of Phobos. Deimos is composed of rock rich in carbonaceous material, much like C-type asteroids and carbonaceous chondrite meteorites. It is cratered, but the surface is noticeably smoother than that of Phobos, caused by the partial filling of craters with regolith. The regolith is highly porous and has a radar-estimated density of only 1.471 g/cm3.\n" +
                "\n" +
                "Escape velocity from Deimos is 5.6 m/s. This velocity could theoretically be achieved by a human performing a vertical jump. The apparent magnitude of Deimos is 12.45." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Io" to listOf(
        "Introduction",

        "Io is the innermost and second-smallest of the four Galilean moons of the planet Jupiter. Slightly larger than Earth's moon, Io is the fourth-largest moon in the Solar System, has the highest density of any moon, the strongest surface gravity of any moon, and the lowest amount of water by atomic ratio of any known astronomical object in the Solar System. It was discovered in 1610 by Galileo Galilei and was named after the mythological character Io, a priestess of Hera who became one of Zeus's lovers." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Europa" to listOf(
        "Introduction",

        "Europa is the smallest of the four Galilean moons orbiting Jupiter, and the sixth-closest to the planet of all the 95 known moons of Jupiter. It is also the sixth-largest moon in the Solar System. Europa was discovered independently by Simon Marius and Galileo Galilei and was named (by Marius) after Europa, the Phoenician mother of King Minos of Crete and lover of Zeus (the Greek equivalent of the Roman god Jupiter).\n" +
                "\n" +
                "Slightly smaller than Earth's Moon, Europa is made of silicate rock and has a water-ice crust and probably an iron–nickel core. It has a very thin atmosphere, composed primarily of oxygen. Its geologically young white-beige surface is striated by light tan cracks and streaks, with very few impact craters. In addition to Earth-bound telescope observations, Europa has been examined by a succession of space-probe flybys, the first occurring in the early 1970s. In September 2022, the Juno spacecraft flew within about 320 km (200 miles) of Europa for a more recent close-up view." + "\n",

        "Physical characteristics",

        "Europa is slightly smaller than the Earth's moon. At just over 3,100 kilometres (1,900 mi) in diameter, it is the sixth-largest moon and fifteenth-largest object in the Solar System. Though by a wide margin the least massive of the Galilean satellites, it is nonetheless more massive than all known moons in the Solar System smaller than itself combined. Its bulk density suggests that it is similar in composition to terrestrial planets, being primarily composed of silicate rock." + "\n",

        "Internal structure",

        "It is estimated that Europa has an outer layer of water around 100 km (62 mi) thick – a part frozen as its crust and a part as a liquid ocean underneath the ice. Recent magnetic-field data from the Galileo orbiter showed that Europa has an induced magnetic field through interaction with Jupiter's, which suggests the presence of a subsurface conductive layer. This layer is likely to be a salty liquid-water ocean. Portions of the crust are estimated to have undergone a rotation of nearly 80°, nearly flipping over (see true polar wander), which would be unlikely if the ice were solidly attached to the mantle. Europa probably contains a metallic iron core." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Ganymede" to listOf(
        "Introduction",

        "Ganymede is the largest and most massive natural satellite of Jupiter, and in the Solar System. Despite being the only moon in the Solar System with a substantial magnetic field, it is the largest Solar System object without a substantial atmosphere. Like Saturn's largest moon Titan, it is larger than the planet Mercury, but has somewhat less surface gravity than Mercury, Io, or the Moon due to its lower density compared to the three. Ganymede orbits Jupiter in roughly seven days and is in a 1:2:4 orbital resonance with the moons Europa and Io, respectively.\n" +
                "\n" +
                "Ganymede is composed of silicate rock and water in approximately equal proportions. It is a fully differentiated body with an iron-rich, liquid metallic core, giving it the lowest moment of inertia factor of any solid body in the Solar System. Its internal ocean potentially contains more water than all of Earth's oceans combined.\n" +
                "\n" +
                "Ganymede's magnetic field is probably created by convection within its core, and influenced by tidal forces from Jupiter's far greater magnetic field. Ganymede has a thin oxygen atmosphere that includes O, O2, and possibly O3 (ozone). Atomic hydrogen is a minor atmospheric constituent. Whether Ganymede has an ionosphere associated with its atmosphere is unresolved." + "\n",

        "Physical characteristics",

        "With a diameter of about 5,270 kilometres (3,270 mi) and a mass of 1.48×1020 tonnes (1.48×1023 kg; 3.26×1023 lb), Ganymede is the largest and most massive moon in the Solar System. It is slightly more massive than the second most massive moon, Saturn's satellite Titan, and is more than twice as massive as the Earth's Moon. It is larger than the planet Mercury, which has a diameter of 4,880 kilometres (3,030 mi) but is only 45 percent of Mercury's mass. Ganymede is the ninth-largest object in the solar system, but the tenth-most massive." + "\n",

        "Internal structure",

        "Ganymede appears to be fully differentiated, with an internal structure consisting of an iron-sulfide–iron core, a silicate mantle, and outer layers of water ice and liquid water. The precise thicknesses of the different layers in the interior of Ganymede depend on the assumed composition of silicates (fraction of olivine and pyroxene) and amount of sulfur in the core. Ganymede has the lowest moment of inertia factor, 0.31, among the solid Solar System bodies. This is a consequence of its substantial water content and fully differentiated interior." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Callisto" to listOf(
        "c",

        "Callisto is the second-largest moon of Jupiter, after Ganymede. In the Solar System it is the third-largest moon after Ganymede and Saturn's largest moon Titan, and nearly as large as the smallest planet Mercury. Callisto is, with a diameter of 4,821 km, roughly a third larger than Earth's Moon and orbits Jupiter on average at a distance of 1,883,000 km, which is about six times further out than the Moon orbiting Earth. It is the outermost of the four large Galilean moons of Jupiter, which were discovered in 1610 with one of the first telescopes, being visible from Earth with common binoculars.\n" +
                "\n" +
                "The surface of Callisto is the oldest and most heavily cratered in the Solar System. Its surface is completely covered with impact craters. It does not show any signatures of subsurface processes such as plate tectonics or volcanism, with no signs that geological activity in general has ever occurred, and is thought to have evolved predominantly under the influence of impacts. Prominent surface features include multi-ring structures, variously shaped impact craters, and chains of craters (catenae) and associated scarps, ridges and deposits. At a small scale, the surface is varied and made up of small, sparkly frost deposits at the tips of high spots, surrounded by a low-lying, smooth blanket of dark material. This is thought to result from the sublimation-driven degradation of small landforms, which is supported by the general deficit of small impact craters and the presence of numerous small knobs, considered to be their remnants. The absolute ages of the landforms are not known. Callisto is composed of approximately equal amounts of rock and ice, with a density of about 1.83 g/cm3, the lowest density and surface gravity of Jupiter's major moons. Compounds detected spectroscopically on the surface include water ice, carbon dioxide, silicates and organic compounds. Investigation by the Galileo spacecraft revealed that Callisto may have a small silicate core and possibly a subsurface ocean of liquid water at depths greater than 100 km." + "\n",

        "Physical characteristics",

        "The average density of Callisto, 1.83 g/cm3, suggests a composition of approximately equal parts of rocky material and water ice, with some additional volatile ices such as ammonia. The mass fraction of ices is 49–55%. The exact composition of Callisto's rock component is not known, but is probably close to the composition of L/LL type ordinary chondrites, which are characterized by less total iron, less metallic iron and more iron oxide than H chondrites. The weight ratio of iron to silicon is 0.9–1.3 in Callisto, whereas the solar ratio is around 1:8.\n" +
                "\n" +
                "Callisto's surface has an albedo of about 20%. Its surface composition is thought to be broadly similar to its composition as a whole. Near-infrared spectroscopy has revealed the presence of water ice absorption bands at wavelengths of 1.04, 1.25, 1.5, 2.0 and 3.0 micrometers. Water ice seems to be ubiquitous on the surface of Callisto, with a mass fraction of 25–50%. The analysis of high-resolution, near-infrared and UV spectra obtained by the Galileo spacecraft and from the ground has revealed various non-ice materials: magnesium- and iron-bearing hydrated silicates, carbon dioxide, sulfur dioxide, and possibly ammonia and various organic compounds. Spectral data indicate that Callisto's surface is extremely heterogeneous at the small scale. Small, bright patches of pure water ice are intermixed with patches of a rock–ice mixture and extended dark areas made of a non-ice material." + "\n",

        "Internal structure",

        "Callisto's battered surface lies on top of a cold, stiff and icy lithosphere that is between 80 and 150 km thick. A salty ocean 150–200 km deep may lie beneath the crust, indicated by studies of the magnetic fields around Jupiter and its moons. It was found that Callisto responds to Jupiter's varying background magnetic field like a perfectly conducting sphere; that is, the field cannot penetrate inside Callisto, suggesting a layer of highly conductive fluid within it with a thickness of at least 10 km. The existence of an ocean is more likely if water contains a small amount of ammonia or other antifreeze, up to 5% by weight. In this case the water+ice layer can be as thick as 250–300 km. Failing an ocean, the icy lithosphere may be somewhat thicker, up to about 300 km.\n" +
                "\n" +
                "Beneath the lithosphere and putative ocean, Callisto's interior appears to be neither entirely uniform nor particularly variable. Galileo orbiter data (especially the dimensionless moment of inertia[h]—0.3549 ± 0.0042—determined during close flybys) suggest that, if Callisto is in hydrostatic equilibrium, its interior is composed of compressed rocks and ices, with the amount of rock increasing with depth due to partial settling of its constituents. In other words, Callisto may be only partially differentiated. The density and moment of inertia for an equilibrium Callisto are compatible with the existence of a small silicate core in the center of Callisto. The radius of any such core cannot exceed 600 km, and the density may lie between 3.1 and 3.6 g/cm3. In this case, Callisto's interior would be in stark contrast to that of Ganymede, which appears to be fully differentiated." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Mimas" to listOf(
        "Introduction",

        "Mimas is the seventh-largest natural satellite of Saturn. With a mean diameter of 396.4 kilometres or 246.3 miles, Mimas is the smallest astronomical body known to be roughly rounded in shape due to its own gravity. Mimas's low density, 1.15 g/cm3, indicates that it is composed mostly of water ice with only a small amount of rock, and study of Mimas's motion suggests that it may have a liquid ocean beneath its surface ice. The surface of Mimas is heavily cratered and shows little signs of recent geological activity. A notable feature of Mimas's surface is Herschel, one of the largest craters relative to the size of the parent body in the Solar System. Herschel measures 139 kilometres (86 miles) across, about one-third of Mimas's mean diameter, and formed from an extremely energetic impact event. The crater's name is derived from the discoverer of Mimas, William Herschel, in 1789. The moon's presence has created one of the largest 'gaps' in Saturn's ring, named the Cassini Division, due to orbital resonance destabilising the particles' orbit there." + "\n",

        "Physical characteristics",

        "The surface area of Mimas is slightly less than the land area of Spain or California. The low density of Mimas, 1.15 g/cm3, indicates that it is composed mostly of water ice with only a small amount of rock. As a result of the tidal forces acting on it, Mimas is noticeably oblate; its longest axis is about 10% longer than the shortest. The ellipsoidal shape of Mimas is especially noticeable in some recent images from the Cassini probe. Mimas's most distinctive feature is a giant impact crater 139 km (86 mi) across, named Herschel after the discoverer of Mimas. Herschel's diameter is almost a third of Mimas's own diameter; its walls are approximately 5 km (3 mi) high, parts of its floor measure 10 km (6 mi) deep, and its central peak rises 6 km (4 mi) above the crater floor. If there were a crater of an equivalent scale on Earth (in relative size) it would be over 4,000 km (2,500 mi) in diameter, wider than Australia. The impact that made this crater must have nearly shattered Mimas: the surface antipodal to Herschel (opposite through the globe) is highly disrupted, indicating that the shock waves created by the Herschel impact propagated through the whole moon." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Enceladus" to listOf(
        "Introduction",

        "Enceladus is the sixth-largest moon of Saturn and the 18th-largest in the Solar System. It is about 500 kilometers (310 miles) in diameter, about a tenth of that of Saturn's largest moon, Titan. It is mostly covered by fresh, clean ice, making it one of the most reflective bodies of the Solar System. Consequently, its surface temperature at noon reaches only −198 °C (75.1 K; −324.4 °F), far colder than a light-absorbing body would be. Despite its small size, Enceladus has a wide variety of surface features, ranging from old, heavily cratered regions to young, tectonically deformed terrain." + "\n",

        "Shape and size",

        "Enceladus is a relatively small satellite composed of ice and rock. It is a scalene ellipsoid in shape; its diameters, calculated from images taken by Cassini's ISS (Imaging Science Subsystem) instrument, are 513 km between the sub- and anti-Saturnian poles, 503 km between the leading and trailing hemispheres, and 497 km between the north and south poles.\n" +
                "\n" +
                "Enceladus is only one-seventh the diameter of Earth's Moon. It ranks sixth in both mass and size among the satellites of Saturn, after Titan (5,150 km), Rhea (1,530 km), Iapetus (1,440 km), Dione (1,120 km) and Tethys (1,050 km)." + "\n",

        "Internal structure",

        "Before the Cassini mission, little was known about the interior of Enceladus. However, flybys by Cassini provided information for models of Enceladus's interior, including a better determination of the mass and shape, high-resolution observations of the surface, and new insights on the interior.\n" +
                "\n" +
                "Initial mass estimates from the Voyager program missions suggested that Enceladus was composed almost entirely of water ice. However, based on the effects of Enceladus's gravity on Cassini, its mass was determined to be much higher than previously thought, yielding a density of 1.61 g/cm3. This density is higher than those of Saturn's other mid-sized icy satellites, indicating that Enceladus contains a greater percentage of silicates and iron.\n" +
                "\n" +
                "Castillo, Matson et al. (2005) suggested that Iapetus and the other icy satellites of Saturn formed relatively quickly after the formation of the Saturnian subnebula, and thus were rich in short-lived radionuclides. These radionuclides, like aluminium-26 and iron-60, have short half-lives and would produce interior heating relatively quickly. Without the short-lived variety, Enceladus's complement of long-lived radionuclides would not have been enough to prevent rapid freezing of the interior, even with Enceladus's comparatively high rock–mass fraction, given its small size.\n" +
                "\n" +
                "Given Enceladus's relatively high rock–mass fraction, the proposed enhancement in 26Al and 60Fe would result in a differentiated body, with an icy mantle and a rocky core. Subsequent radioactive and tidal heating would raise the temperature of the core to 1,000 K, enough to melt the inner mantle. For Enceladus to still be active, part of the core must have also melted, forming magma chambers that would flex under the strain of Saturn's tides. Tidal heating, such as from the resonance with Dione or from libration, would then have sustained these hot spots in the core and would power the current geological activity.\n" +
                "\n" +
                "In addition to its mass and modeled geochemistry, researchers have also examined Enceladus's shape to determine if it is differentiated. Porco, Helfenstein et al. (2006) used limb measurements to determine that its shape, assuming hydrostatic equilibrium, is consistent with an undifferentiated interior, in contradiction to the geological and geochemical evidence. However, the current shape also supports the possibility that Enceladus is not in hydrostatic equilibrium, and may have rotated faster at some point in the recent past (with a differentiated interior). Gravity measurements by Cassini show that the density of the core is low, indicating that the core contains water in addition to silicates." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),
    "Tethys" to listOf(
        "Introduction",

        "Tethys is the fifth-largest moon of Saturn, measuring about 1,060 km (660 mi) across. It was discovered by Giovanni Domenico Cassini in 1684, and is named after the titan Tethys of Greek mythology.\n" +
                "\n" +
                "Tethys has a low density of 0.98 g/cm3, the lowest of all the major moons in the solar system, indicating that it is made of water ice with just a small fraction of rock. This was confirmed by the spectroscopy of its surface, which identified water ice as the dominant surface material. A further, smaller amount of an unidentified dark material is present as well. The surface of Tethys is very bright, the second-brightest of the moons of Saturn after Enceladus, and neutral in color.\n" +
                "\n" +
                "Tethys is heavily cratered and cut by a number of large faults and trench-like graben. The largest impact crater, Odysseus, is about 400 km in diameter, whereas the largest graben, Ithaca Chasma, is about 100 km wide and more than 2,000 km long; the two surface features may be related. A small part of the surface is covered by smooth plains that may be cryovolcanic in origin. Like the other regular moons of Saturn, Tethys formed from the Saturnian sub-nebula—a disk of gas and dust that surrounded Saturn soon after its formation." + "\n",

        "Physical characteristics",

        "Tethys is the 16th-largest moon in the Solar System, with a radius of 531 km. Its mass is about 6.17×1020 kg(0.000103 Earth mass), which is less than 1% of the Moon. Despite its relatively small mass, it is more massive than all known moons smaller than itself combined.\n" +
                "\n" +
                "The density of Tethys is 0.98 g/cm3, indicating that it is composed almost entirely of water-ice. It is also the fifth-largest of Saturn's moons. It is not known whether Tethys is differentiated into a rocky core and ice mantle. However, if it is differentiated, the radius of the core does not exceed 145 km, and its mass is below 6% of the total mass. Due to the action of tidal and rotational forces, Tethys has the shape of triaxial ellipsoid. The dimensions of this ellipsoid are consistent with it having a homogeneous interior. The existence of a subsurface ocean—a layer of liquid salt water in the interior of Tethys—is considered unlikely.\n" +
                "\n" +
                "The surface of Tethys is one of the most reflective (at visual wavelengths) in the Solar System, with a visual albedo of 1.229. This very high albedo is the result of the sandblasting of particles from Saturn's E-ring, a faint ring composed of small, water-ice particles generated by Enceladus's south polar geysers. The radar albedo of the Tethyan surface is also very high. The leading hemisphere of Tethys is brighter by 10–15% than the trailing one.\n" +
                "\n" +
                "The high albedo indicates that the surface of Tethys is composed of almost pure water ice with only a small amount of darker materials. The visible spectrum of Tethys is flat and featureless, whereas in the near-infrared strong water ice absorption bands at 1.25, 1.5, 2.0 and 3.0 μm wavelengths are visible. No compound other than crystalline water ice has been unambiguously identified on Tethys. (Possible constituents include organics, ammonia and carbon dioxide.) The dark material in the ice has the same spectral properties as seen on the surfaces of the dark Saturnian moons—Iapetus and Hyperion. The most probable candidate is nanophase iron or hematite. Measurements of the thermal emission as well as radar observations by the Cassini spacecraft show that the icy regolith on the surface of Tethys is structurally complex and has a large porosity exceeding 95%." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),
    "Dione" to listOf(
        "Introduction",

        "Dione is the fourth-largest moon of Saturn. With a mean diameter of 1,123 km and a density of about 1.48 g/cm3, Dione is composed of an icy mantle and crust overlying a silicate rocky core, with rock and water ice roughly equal in mass. Its trailing hemisphere is marked by large cliffs and scarps called chasmata; the trailing hemisphere is also significantly darker compared to the leading hemisphere.\n" +
                "\n" +
                "The moon was discovered by Italian astronomer Giovanni Domenico Cassini in 1684 and is named after the Titaness Dione in Greek mythology. Dione was first imaged up-close by the Voyager 1 space probe in 1980. Later, the Cassini spacecraft made multiple flybys of Dione throughout the 2000s and 2010s as part of its campaign to explore the Saturn system." + "\n",

        "Physical characteristics",

        "At 1,122 km (697 mi) in diameter, Dione is the 15th largest moon in the Solar System, and is more massive than all known moons smaller than itself combined. It is also Saturn's fourth-largest moon. Based on its density, Dione’s interior is likely a combination of silicate rock and water ice in nearly equal parts by mass.\n" +
                "\n" +
                "Shape and gravity observations collected by Cassini suggest a roughly 400 km radius rocky core surrounded by a roughly 160 km envelope of H2O, mainly in the form of water ice, but with some models suggesting that the lowermost part of this layer could be in the form of an internal liquid salt water ocean (a situation similar to that of its orbital resonance partner, Enceladus). Downward bending of the surface associated with the 1.5 km high ridge Janiculum Dorsa can most easily be explained by the presence of such an ocean. Neither moon has a shape close to hydrostatic equilibrium; the deviations are maintained by isostasy. Dione's ice shell is thought to vary in thickness by less than 5%, with the thinnest areas at the poles, where tidal heating of the crust is greatest.\n" +
                "\n" +
                "Though somewhat smaller and denser, Dione is otherwise very similar to Rhea. They both have similar albedo features and varied terrain, and both have dissimilar leading and trailing hemispheres. Dione's leading hemisphere is heavily cratered and is uniformly bright. Its trailing hemisphere, however, contains an unusual and distinctive surface feature: a network of bright ice cliffs." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),



    "Rhea" to listOf(
        "Introduction",

        "Rhea is the second-largest moon of Saturn and the ninth-largest moon in the Solar System, with a surface area that is comparable to the area of Australia. It is the smallest body in the Solar System for which precise measurements have confirmed a shape consistent with hydrostatic equilibrium. Rhea has a nearly circular orbit around Saturn, but it is also tidally locked, like Saturn's other major moons; that is, it rotates with the same period it revolves (orbits), so one hemisphere always faces towards the planet.\n" +
                "\n" +
                "The moon itself has a fairly low density, composed of roughly three-quarters ice and only one-quarter rock. The surface of Rhea is heavily cratered, with distinct leading and trailing hemispheres. Like the moon Dione, it has high-albedo ice cliffs that appear as bright wispy streaks visible from space. The surface temperature varies between −174 °C and −220 °C.\n" +
                "\n" +
                "Rhea was discovered in 1672 by Giovanni Domenico Cassini. Since then, it has been visited by both Voyager probes and was the subject of close targeted flybys by the Cassini orbiter in 2005, 2007, 2010, 2011, and once more in 2013." + "\n",

        "Physical characteristics",

        "Rhea is an icy body with a density of about 1.236 g/cm3. This low density indicates that it is made of ~25% rock (density ~3.25 g/cm3) and ~75% water ice (density ~0.93 g/cm3). A layer of Ice II (a high-pressure and extra-low temperature form of ice) is believed, based on the moon's temperature profile, to start around 350 to 450 kilometres (220 to 280 mi) beneath the surface. Rhea is 1,528 kilometres (949 mi) in diameter, but is still only a third of the size of Titan, Saturn's biggest moon. Although Rhea is the ninth-largest moon, it is only the tenth-most massive moon. Indeed, Oberon, the second-largest moon of Uranus, has almost the same size, but is significantly denser than Rhea (1.63 vs 1.24) and thus more massive, although Rhea is slightly larger by volume. The surface area of the moon can be estimated at 7,330,000 square kilometres (2,830,000 sq mi), similar to Australia (7,688,287 km2)." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Titan" to listOf(
        "Introduction",

        "Titan is the largest moon of Saturn and the second-largest in the Solar System. It is the only moon known to have an atmosphere denser than the Earth's and is the only known object in space—other than Earth—on which there is clear evidence that stable bodies of liquid exist. Titan is one of seven gravitationally rounded moons of Saturn and the second-most distant among them. Frequently described as a planet-like moon, Titan is 50% larger in diameter than Earth's Moon and 80% more massive. It is the second-largest moon in the Solar System after Jupiter's Ganymede and is larger than Mercury; yet Titan is only 40% as massive as Mercury, because Mercury is mainly iron and rock while much of Titan is ice, which is less dense." + "\n",

        "Physical characteristics",

        "Titan is 5,149.46 kilometres (3,199.73 mi) in diameter; it is 6% larger than the planet Mercury and 50% larger than Earth's Moon. Titan is the tenth-largest object known in the Solar system, including the Sun. Before the arrival of Voyager 1 in 1980, Titan was thought to be slightly larger than Ganymede, which has a diameter 5,262 kilometres (3,270 mi), and thus the largest moon in the Solar System. This was an overestimation caused by Titan's dense, opaque atmosphere, with a haze layer 100–200 kilometers above its surface. This increases its apparent diameter. Titan's diameter and mass (and thus its density) are similar to those of the Jovian moons Ganymede and Callisto. Based on its bulk density of 1.881 g/cm3, Titan's composition is 40–60% rock, with the rest being water ice and other materials.\n" +
                "\n" +
                "Titan is probably partially differentiated into distinct layers with a 3,400-kilometre (2,100 mi) rocky center. This rocky center is believed to be surrounded by several layers composed of different crystalline forms of ice, and/or water. The exact structure depends heavily on the heat flux from within Titan itself, which is poorly constrained. The interior may still be hot enough for a liquid layer consisting of a \"magma\" composed of water and ammonia between the ice Ih crust and deeper ice layers made of high-pressure forms of ice. The heat flow from inside Titan may even be too high for high pressure ices to form, with the outermost layers instead consisting primarily of liquid water underneath a surface crust. The presence of ammonia allows water to remain liquid even at a temperature as low as 176 K (−97 °C) (for eutectic mixture with water). The Cassini probe discovered evidence for the layered structure in the form of natural extremely-low-frequency radio waves in Titan's atmosphere. Titan's surface is thought to be a poor reflector of extremely-low-frequency radio waves, so they may instead be reflecting off the liquid–ice boundary of a subsurface ocean. Surface features were observed by the Cassini spacecraft to systematically shift by up to 30 kilometres (19 mi) between October 2005 and May 2007, which suggests that the crust is decoupled from the interior, and provides additional evidence for an interior liquid layer. Further supporting evidence for a liquid layer and ice shell decoupled from the solid core comes from the way the gravity field varies as Titan orbits Saturn Comparison of the gravity field with the RADAR-based topography observations also suggests that the ice shell may be substantially rigid." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Lapetus" to listOf(
        "Introduction",

        "Iapetus is the outermost of Saturn's large moons. With an estimated diameter of 1,469 km (913 mi), it is the third-largest moon of Saturn and the eleventh-largest in the Solar System. Named after the Titan Iapetus, the moon was discovered in 1671 by Giovanni Domenico Cassini.\n" +
                "\n" +
                "A relatively low-density body made up mostly of ice, Iapetus is home to several distinctive and unusual features, such as a striking difference in coloration between its leading hemisphere, which is dark, and its trailing hemisphere, which is bright, as well as a massive equatorial ridge running three-quarters of the way around the moon." + "\n",

        "Physical characteristics",

        "The low density of Iapetus indicates that it is mostly composed of ice, with only a small (~20%) amount of rocky materials.\n" +
                "\n" +
                "Unlike most of the large moons, its overall shape is neither spherical nor ellipsoid, but has a bulging waistline and squashed poles. Its unique equatorial ridge (see below) is so high that it visibly distorts Iapetus's shape even when viewed from a distance. These features often lead it to be characterized as walnut-shaped.\n" +
                "\n" +
                "Iapetus is heavily cratered, and Cassini images have revealed large impact basins, at least five of which are over 350 km (220 mi) wide. The largest, Turgis, has a diameter of 580 km (360 mi); its rim is extremely steep and includes a scarp about 15 km (9.3 mi) high. Iapetus is known to support long-runout landslides or sturzstroms, possibly supported by ice sliding." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),

    "Miranda" to listOf(
        "Introduction",

        "Miranda is the smallest and innermost of Uranus's five round satellites. It was discovered by Gerard Kuiper on 16 February 1948 at McDonald Observatory in Texas, and named after Miranda from William Shakespeare's play The Tempest. Like the other large moons of Uranus, Miranda orbits close to its planet's equatorial plane. Because Uranus orbits the Sun on its side, Miranda's orbit is nearly perpendicular to the ecliptic and shares Uranus's extreme seasonal cycle.\n" +
                "\n" +
                "At just 470 km (290 mi) in diameter, Miranda is one of the smallest closely observed objects in the Solar System that might be in hydrostatic equilibrium (spherical under its own gravity), and its total surface area is roughly equal to that of the U.S. state of Texas. The only close-up images of Miranda are from the Voyager 2 probe, which made observations of Miranda during its Uranus flyby in January 1986. During the flyby, Miranda's southern hemisphere pointed towards the Sun, so only that part was studied." + "\n",

        "Physical characteristics",

        "At 1.15 g/cm3, Miranda is the least dense of Uranus's round satellites. That density suggests a composition of more than 60% water ice. Miranda's surface may be mostly water ice, though it is far rockier than its corresponding satellites in the Saturn system, indicating that heat from radioactive decay may have led to internal differentiation, allowing silicate rock and organic compounds to settle in its interior. Miranda is too small for any internal heat to have been retained over the age of the Solar System. Miranda is the least spherical of Uranus's satellites, with an equatorial diameter 3% wider than its polar diameter. Only water has been detected so far on Miranda's surface, though it has been speculated that methane, ammonia, carbon monoxide or nitrogen may also exist at 3% concentrations. These bulk properties are similar to Saturn's moon Mimas, though Mimas is smaller, less dense, and more oblate. A study published in 2024 suggests that Miranda might have had a liquid ocean of about 100 km thinkness beneath the surface within the last 100-500 million years.\n" +
                "\n" +
                "Precisely how a body as small as Miranda could have enough internal energy to produce the myriad geological features seen on its surface is not established with certainty, though the currently favoured hypothesis is that it was driven by tidal heating during a past time when it was in 3:1 orbital resonance with Umbriel. The resonance would have increased Miranda's orbital eccentricity to 0.1, and generated tidal friction due to the varying tidal forces from Uranus. As Miranda approached Uranus, tidal force increased; as it retreated, tidal force decreased, causing flexing that would have warmed Miranda's interior by 20 K, enough to trigger melting. The period of tidal flexing could have lasted for up to 100 million years. Also, if clathrate existed within Miranda, as has been hypothesised for the satellites of Uranus, it may have acted as an insulator, since it has a lower conductivity than water, increasing Miranda's temperature still further. Miranda may have also once been in a 5:3 orbital resonance with Ariel, which would have also contributed to its internal heating. However, the maximum heating attributable to the resonance with Umbriel was likely about three times greater." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Ariel" to listOf(
        "Introduction",

        "Ariel is the fourth-largest moon of Uranus. Ariel orbits and rotates in the equatorial plane of Uranus, which is almost perpendicular to the orbit of Uranus, so the moon has an extreme seasonal cycle.\n" +
                "\n" +
                "It was discovered on 24 October 1851  by William Lassell and named for a character in two different pieces of literature. As of 2019, much of the detailed knowledge of Ariel derives from a single flyby of Uranus performed by the space probe Voyager 2 in 1986, which managed to image around 35% of the moon's surface. There are no active plans at present to return to study the moon in more detail, although various concepts such as a Uranus Orbiter and Probe have been proposed.\n" +
                "\n" +
                "After Miranda, Ariel is the second-closest of Uranus's five major rounded satellites. Among the smallest of the Solar System's 20 known spherical moons (it ranks 14th among them in diameter), it is believed to be composed of roughly equal parts ice and rocky material. Its mass is approximately equal in magnitude to Earth's hydrosphere." + "\n",

        "Physical characteristics",

        "Ariel is the fourth-largest of the Uranian moons by size and mass. It is also the 14th-largest moon in the Solar System. The moon's density is 1.52 g/cm3, which indicates that it consists of roughly equal parts water ice and a dense non-ice component. The latter could consist of rock and carbonaceous material including heavy organic compounds known as tholins. The presence of water ice is supported by infrared spectroscopic observations, which have revealed crystalline water ice on the surface of the moon, which is porous and thus transmits little solar heat to layers below. Water ice absorption bands are stronger on Ariel's leading hemisphere than on its trailing hemisphere. The cause of this asymmetry is not known, but it may be related to bombardment by charged particles from Uranus's magnetosphere, which is stronger on the trailing hemisphere (due to the plasma's co-rotation). The energetic particles tend to sputter water ice, decompose methane trapped in ice as clathrate hydrate and darken other organics, leaving a dark, carbon-rich residue behind.\n" +
                "\n" +
                "Except for water, two other compounds have been identified on the surface of Ariel by infrared spectroscopy. The first is carbon dioxide (CO2), which is concentrated mainly on its trailing hemisphere. Ariel shows the strongest spectroscopic evidence for CO2 of any Uranian satellite, and was the first Uranian satellite on which this compound was discovered. The origin of the carbon dioxide is not completely clear. It might be produced locally from carbonates or organic materials under the influence of the energetic charged particles coming from Uranus's magnetosphere or solar ultraviolet radiation. This hypothesis would explain the asymmetry in its distribution, as the trailing hemisphere is subject to a more intense magnetospheric influence than the leading hemisphere. Another possible source is the outgassing of primordial CO2 trapped by water ice in Ariel's interior. The escape of CO2 from the interior may be related to past geological activity on this moon.\n" +
                "\n" +
                "The second compound identified by its feature at wavelength of 2.2 μm on Ariel is ammonia, which is distributed more or less homogeneously over the surface. The presence of ammonia may indicate that Ariel was geologically active in recent past.\n" +
                "\n" +
                "Given its size, rock/ice composition and the possible presence of salt or ammonia in solution to lower the freezing point of water, Ariel's interior may be differentiated into a rocky core surrounded by an icy mantle. If this is the case, the radius of the core (372 km) is about 64% of the radius of the moon, and its mass is around 56% of the moon's mass—the parameters are dictated by the moon's composition. The pressure in the center of Ariel is about 0.3 GPa (3 kbar). The current state of the icy mantle is unclear. The existence of a subsurface ocean is currently considered possible, though a 2006 study suggests that radiogenic heating alone would not be enough to allow for one. More scientific research concluded that an active underwater ocean is possible for the 4 largest moons of Uranus." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Umbriel" to listOf(
        "Introduction",

        "Umbriel is the third-largest moon of Uranus. It was discovered on October 24, 1851, by William Lassell at the same time as neighboring moon Ariel. It was named after a character in Alexander Pope's 1712 poem The Rape of the Lock. Umbriel consists mainly of ice with a substantial fraction of rock, and may be differentiated into a rocky core and an icy mantle. The surface is the darkest among Uranian moons, and appears to have been shaped primarily by impacts, but the presence of canyons suggests early internal processes, and the moon may have undergone an early endogenically driven resurfacing event that obliterated its older surface.\n" +
                "\n" +
                "Covered by numerous impact craters reaching 210 km (130 mi) in diameter, Umbriel is the second-most heavily cratered satellite of Uranus after Oberon. The most prominent surface feature is a ring of bright material on the floor of Wunda crater. This moon, like all regular moons of Uranus, probably formed from an accretion disk that surrounded the planet just after its formation. Umbriel has been studied up close only once, by the spacecraft Voyager 2 in January 1986. It took several images of Umbriel, which allowed mapping of about 40% of the moon's surface." + "\n",

        "Physical characteristics",

        "Umbriel is the third-largest and third-most massive of the Uranian moons. Umbriel is the 13th-largest moon in the Solar System, and it is also the 13th-most massive. The moon's density is 1.54 g/cm3, which indicates that it mainly consists of water ice, with a dense non-ice component constituting around 40% of its mass. The latter could be made of rock and carbonaceous material including heavy organic compounds known as tholins. The presence of water ice is supported by infrared spectroscopic observations, which have revealed crystalline water ice on the surface of the moon. Water ice absorption bands are stronger on Umbriel's leading hemisphere than on the trailing hemisphere. The cause of this asymmetry is not known, but it may be related to the bombardment by charged particles from the magnetosphere of Uranus, which is stronger on the trailing hemisphere (due to the plasma's co-rotation). The energetic particles tend to sputter water ice, decompose methane trapped in ice as clathrate hydrate and darken other organics, leaving a dark, carbon-rich residue behind.\n" +
                "\n" +
                "Except for water, the only other compound identified on the surface of Umbriel by the infrared spectroscopy is carbon dioxide, which is concentrated mainly on the trailing hemisphere. The origin of the carbon dioxide is not completely clear. It might be produced locally from carbonates or organic materials under the influence of the energetic charged particles coming from the magnetosphere of Uranus or the solar ultraviolet radiation. This hypothesis would explain the asymmetry in its distribution, as the trailing hemisphere is subject to a more intense magnetospheric influence than the leading hemisphere. Another possible source is the outgassing of the primordial CO2 trapped by water ice in Umbriel's interior. The escape of CO2 from the interior may be a result of past geological activity on this moon.\n" +
                "\n" +
                "Umbriel may be differentiated into a rocky core surrounded by an icy mantle. If this is the case, the radius of the core (317 km) is about 54% of the radius of the moon, and its mass is around 40% of the moon's mass—the parameters are dictated by the moon's composition. The pressure in the center of Umbriel is about 0.24 GPa (2.4 kbar). The current state of the icy mantle is unclear, although the existence of a subsurface ocean is considered unlikely." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Titania" to listOf(
        "Physical characteristics",

        "Titania is the largest moon of Uranus. At a diameter of 1,578 kilometres (981 mi) it is the eighth largest moon in the Solar System, with a surface area comparable to that of Australia. Discovered by William Herschel in 1787, it is named after the queen of the fairies in Shakespeare's A Midsummer Night's Dream. Its orbit lies inside Uranus's magnetosphere.\n" +
                "\n" +
                "Titania consists of approximately equal amounts of ice and rock, and is probably differentiated into a rocky core and an icy mantle. A layer of liquid water may be present at the core–mantle boundary. Its surface, which is relatively dark and slightly red in color, appears to have been shaped by both impacts and endogenic processes. It is covered with numerous impact craters reaching up to 326 kilometres (203 mi) in diameter, but is less heavily cratered than Oberon, outermost of the five large moons of Uranus. It may have undergone an early endogenic resurfacing event which obliterated its older, heavily cratered surface. Its surface is cut by a system of enormous canyons and scarps, the result of the expansion of its interior during the later stages of its evolution. Like all major moons of Uranus, Titania probably formed from an accretion disk which surrounded the planet just after its formation.\n" +
                "\n" +
                "Infrared spectroscopy conducted from 2001 to 2005 revealed the presence of water ice as well as frozen carbon dioxide on Titania's surface, suggesting it may have a tenuous carbon dioxide atmosphere with a surface pressure of about 10 nanopascals (10−13 bar). Measurements during Titania's occultation of a star put an upper limit on the surface pressure of any possible atmosphere at 1–2 mPa (10–20 nbar). The Uranian system has been studied up close only once, by the spacecraft Voyager 2 in January 1986. It took several images of Titania, which allowed mapping of about 40% of its surface." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Oberon" to listOf(
        "Introduction",

        "Oberon is the outermost and second-largest major moon of the planet Uranus. It is the second-most massive of the Uranian moons, and the tenth-largest moon in the Solar System. Discovered by William Herschel in 1787, Oberon is named after the mythical king of the fairies who appears as a character in Shakespeare's A Midsummer Night's Dream. Its orbit lies partially outside Uranus's magnetosphere." + "\n",

        "Physical characteristics",

        "Oberon is the second-largest and second-most massive of the Uranian moons after Titania, and the ninth-most massive moon in the Solar System. It is the tenth-largest moon by size however, since Rhea, the second-largest moon of Saturn and the ninth-largest moon, is nearly the same size as Oberon although it is about 0.4% larger, despite Oberon having more mass than Rhea. Oberon's density of 1.68 g/cm3, which is higher than the typical density of Saturn's satellites, indicates that it consists of roughly equal proportions of water ice and a dense non-ice component. The latter could be made of rock and carbonaceous material including heavy organic compounds. The presence of water ice is supported by spectroscopic observations, which have revealed crystalline water ice on the surface of the moon. Water ice absorption bands are stronger on Oberon's trailing hemisphere than on the leading hemisphere. This is the opposite of what is observed on other Uranian moons, where the leading hemisphere exhibits stronger water ice signatures. The cause of this asymmetry is not known, but it may be related to impact gardening (the creation of soil via impacts) of the surface, which is stronger on the leading hemisphere. Meteorite impacts tend to sputter (knock out) ice from the surface, leaving dark non-ice material behind. The dark material itself may have formed as a result of radiation processing of methane clathrates or radiation darkening of other organic compounds.\n" +
                "\n" +
                "Oberon may be differentiated into a rocky core surrounded by an icy mantle. If this is the case, the radius of the core (480 km) is about 63% of the radius of the moon, and its mass is around 54% of the moon's mass—the proportions are dictated by the moon's composition. The pressure in the center of Oberon is about 0.5 GPa (5 kbar). The current state of the icy mantle is unclear. If the ice contains enough ammonia or other antifreeze, Oberon may possess a liquid ocean layer at the core–mantle boundary. The thickness of this ocean, if it exists, is up to 40 km and its temperature is around 180 K (close to the water–ammonia eutectic temperature of 176 K). However, the internal structure of Oberon depends heavily on its thermal history, which is poorly known at present. Albeit more recent publications seem to be in favour of active subterranean oceans throughout the larger moons of Uranus." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n"),


    "Triton" to listOf(
        "Introduction",

        "Triton is the largest natural satellite of the planet Neptune. It is the only moon of Neptune massive enough to be rounded under its own gravity and hosts a thin but well-structured atmosphere. Triton orbits Neptune in a retrograde orbit—revolving in the opposite direction to the parent planet's rotation—the only large moon in the Solar System to do so. Triton is thought to have once been a dwarf planet from the Kuiper belt, captured into Neptune's orbit by the latter's gravity." + "\n",

        "Physical characteristics",

        "Triton is the seventh-largest moon and sixteenth-largest object in the Solar System and is modestly larger than the dwarf planets Pluto and Eris. It is also the largest retrograde moon in the Solar System. It accounts for more than 99.5% of all the mass known to orbit Neptune, including the planet's rings and fifteen other known moons, and is also more massive than all known moons in the Solar System smaller than itself combined. Also, with a diameter 5.5% that of Neptune, it is the largest moon of a gas giant relative to its planet in terms of diameter, although Titan is bigger relative to Saturn in terms of mass (the ratio of Triton's mass to that of Neptune is approximately 1:4788). It has a radius, density (2.061 g/cm3), temperature and chemical composition similar to that of Pluto.\n" +
                "\n" +
                "Triton's surface is covered with a transparent layer of annealed frozen nitrogen. Only 40% of Triton's surface has been observed and studied, but it may be entirely covered in such a thin sheet of nitrogen ice. Triton's surface consists of 55% nitrogen ice with other ices mixed in. Water ice comprises 15–35% and frozen carbon dioxide (dry ice) the remaining 10–20%. Trace ices include 0.1% methane and 0.05% carbon monoxide. There could also be ammonia ice on the surface, as there are indications of ammonia dihydrate in the lithosphere. Triton's mean density implies that it probably consists of about 30–45% water ice (including relatively small amounts of volatile ices), with the remainder being rocky material. Triton's surface area is 23 million km2, which is 4.5% of Earth, or 15.5% of Earth's land area. Triton has an unusually high albedo, reflecting 60–95% of the sunlight that reaches it, and it has changed only slightly since the first observations. By comparison, the Moon reflects only 11%. This high albedo causes Triton to reflect a lot of whatever little sunlight there is instead of absorbing it, causing it to have the coldest recorded temperature in the Solar System at 38 K (−235 °C). Triton's reddish color is thought to be the result of methane ice, which is converted to tholins under exposure to ultraviolet radiation." + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n",

        " ",

        " " + "\n")








    )



class solarMap : Fragment() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var leftImage: ImageButton
    private lateinit var rightImage: ImageButton
    private lateinit var upImage: ImageButton
    private lateinit var simImage: ImageButton
    private lateinit var downImage: ImageButton
    private lateinit var sfInfo: ScrollView
    private lateinit var title: EditText

    private lateinit var header1: TextView
    private lateinit var body1: TextView
    private lateinit var pic1: ImageView
    private lateinit var header2: TextView
    private lateinit var body2: TextView
    private lateinit var pic2: ImageView
    private lateinit var header3: TextView
    private lateinit var body3: TextView
    private lateinit var pic3: ImageView
    private lateinit var header4: TextView
    private lateinit var body4: TextView
    private lateinit var pic4: ImageView
    private lateinit var header5: TextView
    private lateinit var body5: TextView
    private lateinit var pic5: ImageView

    private lateinit var frameLayout: FrameLayout

    var currentPlanetIndex = 0
    var currentMoonIndex = 0


    companion object {
        init {
            Utils.init()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {






        val view = inflater.inflate(R.layout.fragment_solar_map, container, false)

        leftImage = view.findViewById(R.id.left_image)
        rightImage = view.findViewById(R.id.right_image)
        upImage = view.findViewById(R.id.up_image)
        simImage = view.findViewById(R.id.imgSim)
        downImage = view.findViewById(R.id.down_image)
        sfInfo = view.findViewById(R.id.SV_info)
        title = view.findViewById(R.id.title)

        header1 = view.findViewById(R.id.header1)
        body1 = view.findViewById(R.id.body1)
        pic1 = view.findViewById(R.id.pic1)
        header2 = view.findViewById(R.id.header2)
        body2 = view.findViewById(R.id.body2)
        pic2 = view.findViewById(R.id.pic2)
        header3 = view.findViewById(R.id.header3)
        body3 = view.findViewById(R.id.body3)
        pic3 = view.findViewById(R.id.pic3)
        header4 = view.findViewById(R.id.header4)
        body4 = view.findViewById(R.id.body4)
        pic4 = view.findViewById(R.id.pic4)
        header5 = view.findViewById(R.id.header5)
        body5 = view.findViewById(R.id.body5)
        pic5 = view.findViewById(R.id.pic5)

        frameLayout = view.findViewById(R.id.fl_wrapper)

        switchPlanetFragment(view, planets[currentPlanetIndex])


        view.findViewById<ImageButton>(R.id.imgSim).setOnClickListener {
            val intent = Intent(requireContext(), ParameterSelectionActivity::class.java)
            startActivity(intent)
        }


        view.findViewById<ImageButton>(R.id.left_image).setOnClickListener {

            currentMoonIndex = 0;
            currentPlanetIndex -= 1;
            switchPlanetFragment(view, planets[currentPlanetIndex])
            ImageButtonsVisibility(currentPlanetIndex,currentMoonIndex)
        }


        view.findViewById<ImageButton>(R.id.right_image).setOnClickListener {

            currentPlanetIndex += 1;
            currentMoonIndex = 0;
            switchPlanetFragment(view, planets[currentPlanetIndex])
            ImageButtonsVisibility(currentPlanetIndex,currentMoonIndex)

        }

        view.findViewById<ImageButton>(R.id.up_image).setOnClickListener {
            currentMoonIndex += 1;
            val planetName = planets[currentPlanetIndex]
            val moonList = moons[planetName]
            switchPlanetFragment(view, moonList!![currentMoonIndex])
            ImageButtonsVisibility(currentPlanetIndex, currentMoonIndex)
        }

        view.findViewById<ImageButton>(R.id.down_image).setOnClickListener {
            currentMoonIndex -= 1;
            val planetName = planets[currentPlanetIndex]
            val moonList = moons[planetName]
            switchPlanetFragment(view, moonList!![currentMoonIndex])
            ImageButtonsVisibility(currentPlanetIndex, currentMoonIndex)
        }
        view.findViewById<ImageButton>(R.id.info_Image).setOnClickListener {
//            private lateinit var header1: TextView
//            private lateinit var body1: TextView
//            private lateinit var header2: TextView
//            private lateinit var body2: TextView
//            private lateinit var header3: TextView
//            private lateinit var body3: TextView
//            private lateinit var header4: TextView
//            private lateinit var body4: TextView
//            private lateinit var header5: TextView
//            private lateinit var body5: TextView





            val planetName = planets[currentPlanetIndex]
            val moonList = moons[planetName]
            val name = moonList!![currentMoonIndex]
            if (currentPlanetIndex==0) {
                title.setText("Solar System")
            }else{
                title.setText(name)
            }
            val text = info[name]
            setPics(name)
            header1.setText(text!![0])
            body1.setText(text!![1])
            header2.setText(text!![2])
            body2.setText(text!![3])
            header3.setText(text!![4])
            body3.setText(text!![5])
            header4.setText(text!![6])
            body4.setText(text!![7])
            header5.setText(text!![8])
            body5.setText(text!![9])

            if(sfInfo.visibility == View.VISIBLE){
                sfInfo.visibility = View.GONE
                frameLayout.visibility = View.VISIBLE

                ImageButtonsVisibility(currentPlanetIndex, currentMoonIndex)
            }
            else{
                sfInfo.visibility = View.VISIBLE
                frameLayout.visibility = View.GONE
                leftImage.visibility  = View.GONE
                rightImage.visibility = View.GONE
                upImage.visibility = View.GONE
                downImage.visibility = View.GONE

            }



        }

        return view

    }

    private fun setPics(name: String) {

        when(name){
            "SolarSys" -> {


                pic1.visibility = View.GONE
                pic2.setImageResource(R.drawable.i_solar_system)
                pic2.visibility = View.VISIBLE
                pic3.visibility = View.GONE
                pic4.visibility = View.GONE
                pic5.setImageResource(R.drawable.solar_system_d)
                pic5.visibility = View.VISIBLE
            }
            "Sun" -> {


                pic1.visibility = View.GONE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.sun_poster)
                pic3.visibility = View.VISIBLE
                pic4.setImageResource(R.drawable.the_life_cycle_of_a_sun)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Mercury" -> {

                pic1.setImageResource(R.drawable.mercury_in_true_color)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.setImageResource(R.drawable.mercury_with)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Venus" -> {

                pic1.setImageResource(R.drawable.venus_2_approach_image)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.setImageResource(R.drawable.interiorofvenus)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Earth" -> {
                pic1.setImageResource(R.drawable.the_earth_seen_from_apollo_17)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.earth2014shape)
                pic3.visibility = View.VISIBLE
                pic4.setImageResource(R.drawable.earth_cutaway)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }

            "Mars" -> {
                pic1.setImageResource(R.drawable.mars_valles_marineris)
                pic1.visibility = View.VISIBLE
            pic2.visibility = View.GONE
            pic3.visibility = View.GONE
            pic4.setImageResource(R.drawable.mars_internal_structure)
            pic4.visibility = View.VISIBLE
            pic5.visibility = View.GONE
        }
            "Jupiter" -> {
                pic1.setImageResource(R.drawable.jupiter_and_its_shrunken_great_red_spot)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.setImageResource(R.drawable.jupiter_earth_moon_comparison)
                pic4.visibility = View.VISIBLE
                pic5.setImageResource(R.drawable.jupiter_diagram)
                pic5.visibility = View.VISIBLE
            }
            "Saturn" -> {
                pic1.setImageResource(R.drawable.saturn_during_equinox)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.setImageResource(R.drawable.saturn_diagram_svg)
                pic4.visibility = View.VISIBLE
                pic5.setImageResource(R.drawable.saturn_s_rings_pia03550)
                pic5.visibility = View.VISIBLE
            }
            "Uranus" -> {
                pic1.setImageResource(R.drawable.uranus_voyager2_color_calibrated)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.setImageResource(R.drawable.uranus_diagram_svg)
                pic4.visibility = View.VISIBLE
                pic5.setImageResource(R.drawable.uranian_moon_montage__albedo_corrected)
                pic5.visibility = View.VISIBLE
            }
            "Neptune" -> {
                pic1.setImageResource(R.drawable.neptune_voyager2_color_calibrated)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.neptune__earth_size_comparison_true_color)
                pic3.visibility = View.VISIBLE
                pic4.setImageResource(R.drawable._920px_another_neptune_diagram_svg)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Moon" -> {
                pic1.setImageResource(R.drawable.fullmoon2010)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.setImageResource(R.drawable.return_of_the_moon_diagram_svg)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Phobos" -> {
                pic1.setImageResource(R.drawable.phobos_colour_2008)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.moon_phobos_deimos)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }

            "Deimos" -> {
            pic1.setImageResource(R.drawable.nasa_deimos_marsmoon_20090221)
            pic1.visibility = View.VISIBLE
            pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.moon_phobos_deimos)
            pic3.visibility = View.VISIBLE
            pic4.visibility = View.GONE
            pic5.visibility = View.GONE
        }
            "Io" -> {
                pic1.setImageResource(R.drawable.io_highest_resolution_true_color)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }

            "Europa" -> {
                pic1.setImageResource(R.drawable.europa___perijove_45__cropped_)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.europa__earth___moon_size_comparison)
                pic3.visibility = View.VISIBLE
                pic4.setImageResource(R.drawable.europa_poster)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Ganymede" -> {
                pic1.setImageResource(R.drawable.ganymede___perijove_34_composite)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.visibility = View.GONE
                pic4.setImageResource(R.drawable.ganymede_diagram_svg)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Callisto" -> {
                pic1.setImageResource(R.drawable._024px_callisto___july_8_1979__38926064465_)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.callisto__earth___moon_size_comparison)
                pic3.visibility = View.VISIBLE
                pic4.setImageResource(R.drawable.callisto_diagram_svg)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Mimas" -> {
                pic1.setImageResource(R.drawable._024px_mimas_cassini)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.earth_moon_mimas)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }
            "Enceladus"-> {
                pic1.setImageResource(R.drawable.pia17202___approaching_enceladus)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.enceladus_size_corrected)
                pic3.visibility = View.VISIBLE
                pic4.setImageResource(R.drawable.pia19656_saturnmoon_enceladus_ocean_artconcept_20150915)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Tethys"-> {
                pic1.setImageResource(R.drawable.pia18317_saturnmoon_tethys_cassini_20150411)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.tethys_size_corrected)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }
            "Dione"-> {
                pic1.setImageResource(R.drawable.dione_in_natural_light__cropped_)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.dione__earth___moon_size_comparison)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }
            "Rhea"-> {
                pic1.setImageResource(R.drawable.pia07763_rhea_full_globe5)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.rhea__earth___moon_size_comparison)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }
            "Titan"-> {
                pic1.setImageResource(R.drawable.titan_in_true_color_by_kevin_m__gill)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.titan__earth___moon_size_comparison)
                pic3.visibility = View.VISIBLE
                pic4.setImageResource(R.drawable.titan_poster_svg)
                pic4.visibility = View.VISIBLE
                pic5.visibility = View.GONE
            }
            "Lapetus"-> {
                pic1.setImageResource(R.drawable.iapetus_as_seen_by_the_cassini_probe___20071008)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.iapetus__earth__moon_size_comparison)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }
            "Miranda"-> {
                pic1.setImageResource(R.drawable.miranda_mosaic_in_color___voyager_2)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.miranda_moon_earth)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }
            "Ariel"-> {
                pic1.setImageResource(R.drawable.ariel_in_monochrome)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.ariel_earth_moon_comparison)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }
            "Umbriel"-> {
                pic1.setImageResource(R.drawable.pia00040_umbrielx2_47)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.umbriel_earth_moon_comparison)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }

            "Titania"-> {
            pic1.setImageResource(R.drawable.titania__moon__color__cropped)
            pic1.visibility = View.VISIBLE
                pic2.setImageResource(R.drawable.ariel_earth_moon_comparison)
            pic2.visibility = View.VISIBLE
            pic3.visibility = View.GONE
            pic4.visibility = View.GONE
            pic5.visibility = View.GONE
        }
            "Oberon"-> {
                pic1.setImageResource(R.drawable.oberon_in_true_color_by_kevin_m__gill)
                pic1.visibility = View.VISIBLE
                pic2.visibility = View.GONE
                pic3.setImageResource(R.drawable.oberon_earth_moon_comparison)
                pic3.visibility = View.VISIBLE
                pic4.visibility = View.GONE
                pic5.visibility = View.GONE
            }

            "Triton"-> {
            pic1.setImageResource(R.drawable.triton_usgs23_with_frame)
            pic1.visibility = View.VISIBLE
            pic2.visibility = View.GONE
            pic3.setImageResource(R.drawable.triton__earth___moon_size_comparison)
            pic3.visibility = View.VISIBLE
            pic4.visibility = View.GONE
            pic5.visibility = View.GONE
        }


        }

    }

    private fun ImageButtonsVisibility(currentPlanetIndex: Int, currentMoonIndex: Int) {
        //  settings for visibility
        leftImage.visibility = if ((currentPlanetIndex > 0) and  (currentMoonIndex == 0)) View.VISIBLE else View.GONE
        rightImage.visibility = if ((currentPlanetIndex < planets.size - 1) and (currentMoonIndex == 0)) View.VISIBLE else View.GONE

        val planetName = planets[currentPlanetIndex]
        val moonList = moons[planetName]

        //  up/down visibility
        if (moonList != null && moonList.size > 1) {
            upImage.visibility = if (currentMoonIndex < moonList.size - 1) View.VISIBLE else View.GONE
            downImage.visibility = if (currentMoonIndex > 0) View.VISIBLE else View.GONE
        } else {
            //  up/down buttons if no moons
            upImage.visibility = View.GONE
            downImage.visibility = View.GONE
        }
    }



    private fun switchPlanetFragment(view: View?, s: String) {
        val fragment = when (s) {
            "SolarSys" -> SolarSys()
            "Sun" -> Sun()
            "Mercury" -> Mercury()
            "Venus" -> Venus()
            "Earth" -> Earth()
            "Mars" -> Mars()
            "Jupiter" -> Jupiter()
            "Saturn" -> Saturn()
            "Uranus" -> Uranus()
            "Neptune" -> Neptune()
            "Moon" -> EarthMoon()
            "Phobos" -> MarsDeimos()
            "Deimos" -> MarsPhobos()
            "Io" -> JupiterIo()
            "Europa" -> JupiterEuropa()
            "Ganymede" -> JupiterGanymede()
            "Callisto" -> JupiterCallisto()
            "Mimas" -> SaturnMimas()
            "Enceladus" -> SaturnEnceladus()
            "Tethys" -> SaturnTethys()
            "Dione" -> SaturnDione()
            "Rhea" -> SaturnRhea()
            "Titan" -> SaturnTitan()
            "Lapetus" -> SaturnLapetus()
            "Miranda" -> UranusMiranda()
            "Ariel" -> UranusAriel()
            "Umbriel" -> UranusUmbriel()
            "Titania" -> UranusTitania()
            "Oberon" -> UranusOberon()
            "Triton" -> NeptuneTriton()


            else -> null
        }

        fragment?.let {
            childFragmentManager.beginTransaction().apply {
                replace(R.id.fl_wrapper, it)
                commit()
            }
        }
    }



}

//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA




