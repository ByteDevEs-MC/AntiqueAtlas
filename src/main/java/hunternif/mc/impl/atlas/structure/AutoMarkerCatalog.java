package hunternif.mc.impl.atlas.structure;

import java.util.List;

/**
 * The structures and world generation features that get automatic markers, grouped into config
 * categories. Each {@link Switch} is one on/off option in the config; it can cover several ids
 * (for example all four Mayan Temple variants). Ids of mods that aren't installed are simply ignored.
 * Anything not listed here can still be added with the extraStructureMarkers / extraFeatureMarkers lists.
 * <p>
 * Based on Nischhelm's AntiqueAtlasAutoMarker for 1.12.2 (https://github.com/Nischhelm/AntiqueAtlasAutoMarker),
 * which has the same per-structure settings (on/off, marker icon, label). The list, icons and labels follow
 * its config as set up in RLCraft Dregora.
 */
public final class AutoMarkerCatalog {

    /** One structure or feature id with the label its marker gets. */
    public record Target(String id, String label) {}

    /** One config switch: its key, marker type (without a namespace it means antiqueatlas:), and the ids it covers. */
    public record Switch(String key, String markerType, boolean feature, boolean enabledByDefault, List<Target> targets) {
        /** Text for the config comment: the label(s) and the ids this switch covers. */
        public String description() {
            String first = targets.get(0).label();
            boolean sameLabel = targets.stream().allMatch(t -> t.label().equals(first));
            if (sameLabel) {
                String ids = String.join(", ", targets.stream().map(Target::id).toList());
                return (first.isEmpty() ? key : first) + " (" + ids + ")";
            }
            // Different labels: list each one with its id.
            return String.join(", ", targets.stream().map(t -> t.label() + " (" + t.id() + ")").toList());
        }
    }

    /** One config section, for example autoMarkers.dungeonsArise. */
    public record Category(String key, String comment, List<Switch> switches) {}

    // Builders that keep the table below short.
    private static Switch s(String key, String marker, String id, String label) {
        return new Switch(key, marker, false, true, List.of(new Target(id, label)));
    }

    /** Several ids under one switch, all with the same label. */
    private static Switch sm(String key, String marker, String label, String... ids) {
        return new Switch(key, marker, false, true, java.util.Arrays.stream(ids).map(id -> new Target(id, label)).toList());
    }

    /** Several ids under one switch, each with its own label. */
    private static Switch sl(String key, String marker, Target... targets) {
        return new Switch(key, marker, false, true, List.of(targets));
    }

    private static Target t(String id, String label) {
        return new Target(id, label);
    }

    /** A world generation feature instead of a structure. */
    private static Switch f(String key, String marker, String label, String... ids) {
        return new Switch(key, marker, true, true, java.util.Arrays.stream(ids).map(id -> new Target(id, label)).toList());
    }

    public static final List<Category> CATEGORIES = List.of(
            new Category("vanilla", "Vanilla structures. Villages are controlled by gameplay.autoVillageMarkers.", List.of(
                    s("woodlandMansion", "mansion", "minecraft:mansion", "Woodland Mansion"),
                    s("oceanMonument", "monument", "minecraft:monument", "Ocean Monument"),
                    s("stronghold", "dungeon", "minecraft:stronghold", "Stronghold"),
                    s("ancientCity", "dungeon", "minecraft:ancient_city", "Ancient City"),
                    s("pillagerOutpost", "tower", "minecraft:pillager_outpost", "Pillager Outpost"),
                    s("desertPyramid", "dungeon", "minecraft:desert_pyramid", "Desert Pyramid"),
                    s("jungleTemple", "dungeon", "minecraft:jungle_pyramid", "Jungle Temple"),
                    s("witchHut", "wizardtower", "minecraft:swamp_hut", "Witch Hut"),
                    s("netherFortress", "sword", "minecraft:fortress", "Nether Fortress"),
                    s("bastionRemnant", "bastion", "minecraft:bastion_remnant", "Bastion Remnant"),
                    s("endCity", "end_city", "minecraft:end_city", ""))),

            new Category("rlcraftTowns", "RLCraft towns and cities (RLStructure).", List.of(
                    s("aeriveth", "google", "rec_migrated:p_aeriveth", "Aeriveth"),
                    s("iceCity", "google", "rec_migrated:p_icecity", "Ice City"),
                    s("sanctuaryOfAdmys", "google", "rec_migrated:p_sanctuaryofadmys", "Sanctuary of Admys"),
                    s("meridor", "google", "rec_migrated:p_townmeridor", "Meridor"),
                    s("updateBuild", "google", "rec_migrated:rlcraft_2_9_update_build", "RLCraft 2.9 Update Build"),
                    s("shivaxiMonument", "google", "rec_migrated:shivaxi", "Shivaxi Monument"),
                    s("seasidePort", "village", "rec_migrated:p_seasideport", "Seaside Port"),
                    s("plainsCity", "village", "rec_migrated:p_plainscity", "Plains City"),
                    s("islandCity", "village", "rec_migrated:p_islandcity", "Island City"),
                    s("jungleVillage", "village", "rec_migrated:p_junglevillage", "Jungle Village"),
                    s("deqing", "village", "rec_migrated:p_deqing", "Deqing"),
                    s("smallTown", "village", "rec_migrated:rlcraft_smalltown", "Small Town"),
                    s("desertTown", "village", "rec_migrated:rlcraft_deserttown", "Desert Town"),
                    s("undergroundTown", "village", "rec_migrated:rlcraft_largeundergroundtown", "Underground Town"),
                    s("friendlyOutpost", "village", "rec_migrated:rlcraft_friendlyoutpost", "Friendly Outpost"),
                    s("vikingMansion", "village", "rec_migrated:rlcraft_stranding_vikingmansion", "Viking Mansion"),
                    s("hobbitHole", "village", "rec_migrated:hobbithole", "Hobbit Hole"),
                    s("endMegacity", "village", "rec_migrated:rlcraft_end_megacity_start", "End Megacity"))),

            new Category("rlcraftDungeons", "RLCraft dungeons, temples and castles (RLStructure).", List.of(
                    s("skyCastle", "diamond", "rec_migrated:p_skycastle", "Sky Castle"),
                    s("cathedralOfMadness", "dungeon", "rec_migrated:p_cathedralofmadness", "Cathedral of Madness"),
                    s("templeOfErolith", "dungeon", "rec_migrated:p_templeoferolith", "Temple of Erolith"),
                    sm("mayanTemple", "dungeon", "Mayan Temple", "rec_migrated:p_mayantemple1", "rec_migrated:p_mayantemple2",
                            "rec_migrated:p_mayantemple3", "rec_migrated:p_mayantemple4"),
                    s("desertTemple", "dungeon", "rec_migrated:p_deserttemple", "Desert Temple"),
                    s("snowTemple", "dungeon", "rec_migrated:p_snowtemple", "Snow Temple"),
                    s("desertDungeon", "dungeon", "rec_migrated:p_desertdungeon", "Desert Dungeon"),
                    s("treeDungeon", "dungeon", "rec_migrated:p_treedungeon", "Tree Dungeon"),
                    s("witchDungeon", "dungeon", "rec_migrated:p_witchdungeon", "Witch Dungeon"),
                    s("forestGuardian", "dungeon", "rec_migrated:p_forestguardian", "Forest Guardian"),
                    s("forestStronghold", "sword", "rec_migrated:rlcraft_foreststronghold", "Forest Stronghold"),
                    s("netherCityChurch", "dungeon", "rec_migrated:rlcraft_nethercity_church", "Nether City Church"),
                    s("cultIsland", "skull", "rec_migrated:p_cultisland", "Cult Island"),
                    s("spookyHouse", "skull", "rec_migrated:p_spookyhouse", "Spooky House"),
                    sm("defiledCult", "defiled", "Defiled Cult", "rec_migrated:rlcraft_defiled_cult", "rec_migrated:rlcraft_defiled_cult_og"),
                    s("endIslandCastle", "dungeon", "rec_migrated:rlcraft_end_endislandcastle", "End Island Castle"),
                    s("flippedCastle", "dungeon", "rec_migrated:rlcraft_end_flipped_castle", "Flipped Castle"))),

            new Category("rlcraftTowers", "RLCraft towers (RLStructure).", List.of(
                    s("wizardTower", "wizardtower", "rec_migrated:p_wizardtower", "Wizard Tower"),
                    s("witchTower", "wizardtower", "rec_migrated:witchtower", "Witch Tower"),
                    s("threeTowers", "quadtower", "rec_migrated:threetowers", "Three Towers"),
                    s("fourTowers", "quadtower", "rec_migrated:rlcraft_4towers", "Four Towers"),
                    s("lighthouse", "tower", "rec_migrated:p_lighthouse", "Lighthouse"),
                    s("spiralPortalTower", "tower", "rec_migrated:rlcraft_end_spiral_portal_tower", "Spiral Portal Tower"),
                    s("brokenBattleTower", "tower", "rec_migrated:rlcraft_end_battletower_broken", "Broken Battle Tower"))),

            new Category("rlcraftShips", "RLCraft ships (RLStructure).", List.of(
                    s("largeShip", "ship", "rec_migrated:p_largeship", "Large Ship"),
                    s("smallShip", "ship", "rec_migrated:p_smallship", "Small Ship"),
                    s("flagship", "ship", "rec_migrated:flagship", "Flagship"),
                    s("pirateHideout", "ship", "rec_migrated:piratehideout", "Pirate Hideout"))),

            new Category("rlcraftPortalsAndRuins", "RLCraft portals and ruins (RLStructure).", List.of(
                    sm("netherPortal", "nether_portal", "Nether Portal", "rec_migrated:rlcraft_netherportal1_chiom", "rec_migrated:rlcraft_netherportal2_felix"),
                    s("ancientTeleporter", "nether_portal", "rec_migrated:p_ancientteleporter", "Ancient Teleporter"),
                    s("parkourRuins", "ruins", "rec_migrated:rlcraft_ruinsparkour", "Parkour Ruins"),
                    s("fortRuins", "ruins", "rec_migrated:smallfortruinsjapanese", "Fort Ruins"))),

            new Category("battleTowers", "Battle Towers.", List.of(
                    sm("battleTower", "tower", "Battle Tower", "fbt:tall_battletower", "fbt:deep_battletower"),
                    s("megaBattleTower", "megatower", "fbt:mega_battletower", "Mega Battle Tower"))),

            new Category("iceAndFire", "Ice and Fire. Dragon roosts and caves, the hydra and cyclops caves, siren islands and myrmex hives are world generation features.", List.of(
                    s("gorgonTemple", "dungeon", "iceandfire:gorgon_temple", "Gorgon Temple"),
                    s("graveyard", "tomb", "iceandfire:graveyard", "Graveyard"),
                    s("mausoleum", "tomb", "iceandfire:mausoleum", "Mausoleum"),
                    f("fireDragonRoost", "dragon_red", "Fire Dragon Roost", "iceandfire:fire_dragon_roost"),
                    f("iceDragonRoost", "dragon_blue", "Ice Dragon Roost", "iceandfire:ice_dragon_roost"),
                    f("lightningDragonRoost", "dragon_gold", "Lightning Dragon Roost", "iceandfire:lightning_dragon_roost"),
                    f("fireDragonCave", "dragon_red", "Fire Dragon Cave", "iceandfire:fire_dragon_cave"),
                    f("iceDragonCave", "dragon_blue", "Ice Dragon Cave", "iceandfire:ice_dragon_cave"),
                    f("lightningDragonCave", "dragon_gold", "Lightning Dragon Cave", "iceandfire:lightning_dragon_cave"),
                    f("hydraCave", "dragon_green", "Hydra Cave", "iceandfire:hydra_cave"),
                    f("cyclopsCave", "cave", "Cyclops Cave", "iceandfire:cyclops_cave"),
                    f("sirenIsland", "skull", "Siren Island", "iceandfire:siren_island"),
                    f("myrmexHive", "monsterspawner", "Myrmex Hive", "iceandfire:myrmex_hive_desert", "iceandfire:myrmex_hive_jungle"))),

            new Category("dungeonsArise", "When Dungeons Arise.", List.of(
                    s("abandonedTemple", "dungeon", "dungeons_arise:abandoned_temple", "Abandoned Temple"),
                    s("aviary", "tower", "dungeons_arise:aviary", "Aviary"),
                    s("banditTowers", "tower", "dungeons_arise:bandit_towers", "Bandit Towers"),
                    s("banditVillage", "village", "dungeons_arise:bandit_village", "Bandit Village"),
                    s("bathhouse", "village", "dungeons_arise:bathhouse", "Bathhouse"),
                    s("ceryneianHind", "dungeon", "dungeons_arise:ceryneian_hind", "Ceryneian Hind"),
                    s("coliseum", "sword", "dungeons_arise:coliseum", "Coliseum"),
                    s("foundry", "dungeon", "dungeons_arise:foundry", "Foundry"),
                    s("greenwoodPub", "village", "dungeons_arise:greenwood_pub", "Greenwood Pub"),
                    s("heavenlyChallenger", "tower", "dungeons_arise:heavenly_challenger", "Heavenly Challenger"),
                    s("heavenlyConqueror", "tower", "dungeons_arise:heavenly_conqueror", "Heavenly Conqueror"),
                    s("heavenlyRider", "tower", "dungeons_arise:heavenly_rider", "Heavenly Rider"),
                    s("illagerCampsite", "sword", "dungeons_arise:illager_campsite", "Illager Campsite"),
                    s("illagerCorsair", "ship", "dungeons_arise:illager_corsair", "Illager Corsair"),
                    s("illagerFort", "sword", "dungeons_arise:illager_fort", "Illager Fort"),
                    s("illagerGalley", "ship", "dungeons_arise:illager_galley", "Illager Galley"),
                    s("illagerWindmill", "tower", "dungeons_arise:illager_windmill", "Illager Windmill"),
                    s("infestedTemple", "dungeon", "dungeons_arise:infested_temple", "Infested Temple"),
                    s("jungleTreeHouse", "tower", "dungeons_arise:jungle_tree_house", "Jungle Tree House"),
                    s("keepKayra", "dungeon", "dungeons_arise:keep_kayra", "Keep Kayra"),
                    s("lighthouse", "tower", "dungeons_arise:lighthouse", "Lighthouse"),
                    s("mechanicalNest", "dungeon", "dungeons_arise:mechanical_nest", "Mechanical Nest"),
                    s("merchantCampsite", "village", "dungeons_arise:merchant_campsite", "Merchant Campsite"),
                    s("miningSystem", "pickaxe", "dungeons_arise:mining_system", "Mining System"),
                    s("monastery", "dungeon", "dungeons_arise:monastery", "Monastery"),
                    s("mushroomMines", "pickaxe", "dungeons_arise:mushroom_mines", "Mushroom Mines"),
                    s("mushroomVillage", "village", "dungeons_arise:mushroom_village", "Mushroom Village"),
                    s("plagueAsylum", "dungeon", "dungeons_arise:plague_asylum", "Plague Asylum"),
                    s("scorchedMines", "pickaxe", "dungeons_arise:scorched_mines", "Scorched Mines"),
                    s("shirazPalace", "dungeon", "dungeons_arise:shiraz_palace", "Shiraz Palace"),
                    s("smallBlimp", "ship", "dungeons_arise:small_blimp", "Blimp"),
                    s("thornbornTowers", "tower", "dungeons_arise:thornborn_towers", "Thornborn Towers"),
                    s("typhon", "ship", "dungeons_arise:typhon", "Typhon"),
                    s("undeadPirateShip", "ship", "dungeons_arise:undead_pirate_ship", "Undead Pirate Ship"))),

            new Category("cataclysm", "L_Ender's Cataclysm.", List.of(
                    s("abandonedSpire", "tower", "cataclysm:abandoned_spire", "Abandoned Spire"),
                    s("abandonedTemple", "dungeon", "cataclysm:abandoned_temple", "Abandoned Temple"),
                    s("abandonedVillage", "village", "cataclysm:abandoned_village", "Abandoned Village"),
                    s("acropolis", "dungeon", "cataclysm:acropolis", "Acropolis"),
                    s("amethystNest", "cave", "cataclysm:amethyst_nest", "Amethyst Nest"),
                    s("ancientFactory", "dungeon", "cataclysm:ancient_factory", "Ancient Factory"),
                    s("burningArena", "dungeon", "cataclysm:burning_arena", "Burning Arena"),
                    s("cursedPyramid", "dungeon", "cataclysm:cursed_pyramid", "Cursed Pyramid"),
                    s("occupiedVillage", "village", "cataclysm:desert_occupied_village", "Occupied Village"),
                    s("desertSite", "ruins", "cataclysm:desert_site", "Desert Site"),
                    s("frostedPrison", "dungeon", "cataclysm:frosted_prison", "Frosted Prison"),
                    s("ruinedCitadel", "dungeon", "cataclysm:ruined_citadel", "Ruined Citadel"),
                    s("soulBlacksmith", "anvil", "cataclysm:soul_black_smith", "Soul Blacksmith"),
                    s("sunkenCity", "dungeon", "cataclysm:sunken_city", "Sunken City"))),

            new Category("wizardry", "Electroblob's Wizardry.", List.of(
                    s("wizardTower", "wizardtower", "ebwizardry:wizard_tower", "Wizard Tower"),
                    s("libraryRuins", "book", "ebwizardry:library_ruins", "Library Ruins"),
                    sl("shrines", "ruins",
                            t("ebwizardry:earth_shrine", "Earth Shrine"), t("ebwizardry:fire_shrine", "Fire Shrine"),
                            t("ebwizardry:healing_shrine", "Healing Shrine"), t("ebwizardry:ice_shrine", "Ice Shrine"),
                            t("ebwizardry:lightning_shrine", "Lightning Shrine"), t("ebwizardry:necromancy_shrine", "Necromancy Shrine"),
                            t("ebwizardry:sorcery_shrine", "Sorcery Shrine")))),

            new Category("illagers", "Illager Warship and It Takes a Pillage.", List.of(
                    s("illagerWarship", "ship", "illagerwarship:warship", "Illager Warship"),
                    s("bastille", "sword", "takesapillage:bastille", "Bastille"),
                    s("pillagerCamp", "sword", "takesapillage:pillager_camp", "Pillager Camp"))),

            new Category("moogs", "Moog's End, Nether and Voyager Structures.", List.of(
                    s("phantomCitadel", "dungeon", "mes:phantom_citadel", "Phantom Citadel"),
                    s("starlightVoyager", "ship", "mes:starlight_voyager", "Starlight Voyager"),
                    sm("endShip", "ship", "End Ship", "mes:mega_ship", "mes:mega_ship_basic", "mes:mega_ship_deepslate",
                            "mes:mega_ship_deepslate_2", "mes:mega_ship_deepslate_3"),
                    sm("crashedEndShip", "ship", "Crashed End Ship", "mes:mega_ship_crashed", "mes:mega_ship_crashed_2", "mes:mega_ship_crashed_deepslate"),
                    s("megaFortress", "dungeon", "mns:mega_fortress", "Mega Fortress"),
                    s("dragonArena", "sword", "mns:dragon_arena", "Dragon Arena"),
                    s("arena", "sword", "mns:large_arena", "Arena"),
                    s("castleRuins", "ruins", "mvs:castle_ruins", "Castle Ruins"),
                    s("cathedral", "dungeon", "mvs:cathedral", "Cathedral"),
                    s("cartographerTower", "tower", "mvs:cartographer_tower", "Cartographer Tower"))),

            new Category("villages", "Villages and cities from other mods.", List.of(
                    s("hobbitVillage", "village", "lios_outlandish_villages:hobbit_village", "Hobbit Village"),
                    sm("spiralTowerVillage", "village", "Spiral Tower Village", "lios_outlandish_villages:spiral_tower_village",
                            "lios_outlandish_villages:spiral_tower_village_sea"),
                    sm("bwgVillages", "village", "Village", "biomeswevegone:village/forgotten", "biomeswevegone:village/pumpkin_patch",
                            "biomeswevegone:village/red_rock", "biomeswevegone:village/salem", "biomeswevegone:village/skyris",
                            "biomeswevegone:village/swamp"),
                    s("endVillage", "village", "betterend:end_village", "End Village"),
                    s("netherCity", "village", "betternether:nether_city", "Nether City"))),

            new Category("otherDungeons", "Dungeons and towers from other mods.", List.of(
                    s("netherJungleTemple", "dungeon", "betternether:jungle_temples", "Nether Jungle Temple"),
                    s("doomlikeDungeon", "dungeon", "dldungeonsjbg:dldungeon", "Dungeon"),
                    s("dungeonCrawl", "dungeon", "dungeoncrawl:dungeon", "Dungeon Crawl"),
                    sm("oceanDungeon", "dungeon", "Ocean Dungeon", "seafaring:mossy_ocean_dungeon", "seafaring:mossy_ocean_dungeon2",
                            "seafaring:sandstone_ocean_dungeon", "seafaring:sandstone_ocean_dungeon2"),
                    s("seafaringUndergroundDungeon", "dungeon", "seafaring:undergrounddungeon", "Underground Dungeon"),
                    sm("towersOfTheWild", "tower", "Tower", "totw_additions:end_tower", "totw_additions:nether_tower"))),

            new Category("additionalStructures", "Additional Structures.", List.of(
                    s("mayaTemple", "dungeon", "additionalstructures:maya_temple", "Maya Temple"),
                    sm("pyramid", "dungeon", "Pyramid", "additionalstructures:pyramid_1", "additionalstructures:pyramid_2"),
                    s("pyramidDungeon", "dungeon", "additionalstructures:pyramid_dungeon", "Pyramid Dungeon"),
                    s("sphinx", "dungeon", "additionalstructures:sphinx", "Sphinx"),
                    sm("undergroundDungeon", "dungeon", "Underground Dungeon", "additionalstructures:underground_dungeon_1",
                            "additionalstructures:underground_dungeon_2"),
                    s("endDungeon", "dungeon", "additionalstructures:end_dungeon_1", "End Dungeon"),
                    sm("netherDungeon", "dungeon", "Nether Dungeon", "additionalstructures:nether_dungeon_1",
                            "additionalstructures:nether_dungeon_2", "additionalstructures:nether_dungeon_3")))
    );

    private AutoMarkerCatalog() {}
}
