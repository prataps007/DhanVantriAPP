package com.example.plantimagerecognitionusingml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class PlantsInfo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plants_info)

        var plant:TextView = findViewById(R.id.plantNameTextView)
        var description:TextView = findViewById(R.id.plantDescriptionTextView)
        var image:ImageView = findViewById(R.id.plantImageView)

        val receivedData = intent.getStringExtra("Plant")

        if (receivedData != null) {
            if(receivedData=="Neem"){
                image.setImageResource(R.drawable.neempic)
                plant.setText("Neem")
                description.setText("Azadirachta indica, commonly known as neem, has attracted worldwide prominence in recent years, owing to its wide range of medicinal properties. Neem has been extensively used in Ayurveda, Unani and Homoeopathic medicine and has become a cynosure of modern medicine. Neem elaborates a vast array of biologically active compounds that are chemically diverse and structurally complex. More than 140 compounds have been isolated from different parts of neem. All parts of the neem tree- leaves, flowers, seeds, fruits, roots and bark have been used traditionally for the treatment of inflammation, infections, fever, skin diseases and dental disorders. The medicinal utilities have been described especially for neem leaf. Neem leaf and its constituents have been demonstrated to exhibit immunomodulatory, anti-inflammatory, antihyperglycaemic, antiulcer, antimalarial, antifungal, antibacterial, antiviral, antioxidant, antimutagenic and anticarcinogenic properties. This review summarises the wide range of pharmacological activities of neem leaf.\n" +
                        "\n")
            }
            if(receivedData=="Aloevera"){
                image.setImageResource(R.drawable.aloeverapic)
                plant.setText("Aloevera")
                description.setText("Aloe vera has been traditionally used to treat skin injuries (burns, cuts, insect bites, and eczemas) and digestive problems because its anti-inflammatory, antimicrobial, and wound healing properties. Research on this medicinal plant has been aimed at validating traditional uses and deepening the mechanism of action, identifying the compounds responsible for these activities. The most investigated active compounds are aloe-emodin, aloin, aloesin, emodin, and acemannan. Likewise, new actions have been investigated for Aloe vera and its active compounds. This review provides an overview of current pharmacological studies (in vitro, in vivo, and clinical trials), written in English during the last six years (2014–2019). In particular, new pharmacological data research has shown that most studies refer to anti-cancer action, skin and digestive protective activity, and antimicrobial properties. Most recent works are in vitro and in vivo. \n")
            }
            if(receivedData=="Amla"){
                image.setImageResource(R.drawable.amlapic)
                plant.setText("Amla")
                description.setText("Phyllanthus emblica Linn. (syn. Emblica officinalis), commonly known as Indian gooseberry or amla, family Euphorbiaceae, is an important herbal drug used in unani (Graceo - arab) and ayurvedic systems of medicine. The plant is used both as a medicine and as a tonic to build up lost vitality and vigor. Phyllanthus emblica is highly nutritious and could be an important dietary source of vitamin C, amino acids, and minerals. The plant also contains phenolic compounds, tannins, phyllembelic acid, phyllembelin, rutin, curcum-inoids, and emblicol. All parts of the plant are used for medicinal purposes, especially the fruit, which has been used in Ayurveda as a potent rasayana and in traditional medicine for the treatment of diarrhea, jaundice, and inflammation. Various plant parts show antidiabetic, hypolipidemic, antibacterial, antioxidant, antiulcerogenic, hepatoprotective, gastroprotective, and chemopreventive properties. Here we discuss its historical, etymological, morphological and pharmacological aspects.\n")
            }
            if(receivedData=="Tulsi"){
                image.setImageResource(R.drawable.tulsipic)
                plant.setText("Tulsi")
                description.setText("The predominant cause of global morbidity and mortality is lifestyle-related chronic diseases, many of which can be addressed through Ayurveda with its focus on healthy lifestyle practices and regular consumption of adaptogenic herbs. Of all the herbs used within Ayurveda, tulsi (Ocimum sanctum Linn) is preeminent, and scientific research is now confirming its beneficial effects. There is mounting evidence that tulsi can address physical, chemical, metabolic and psychological stress through a unique combination of pharmacological actions. Tulsi has been found to protect organs and tissues against chemical stress from industrial pollutants and heavy metals, and physical stress from prolonged physical exertion, ischemia, physical restraint and exposure to cold and excessive noise. Tulsi has also been shown to counter metabolic stress through normalization of blood glucose, blood pressure and lipid levels, and psychological stress through positive effects on memory and cognitive function and through its anxiolytic and anti-depressant properties. Tulsi's broad-spectrum antimicrobial activity, which includes activity against a range of human and animal pathogens, suggests it can be used as a hand sanitizer, mouthwash and water purifier as well as in animal rearing, wound healing, the preservation of food stuffs and herbal raw materials and traveler's health.")
            }
            if(receivedData=="Peppermint"){
                image.setImageResource(R.drawable.tulsipic)
                plant.setText("Peppermint")
                description.setText("The herb peppermint, a natural cross between two types of mint (water mint and spearmint), grows throughout Europe and North America.\n" +
                        "Both peppermint leaves and the essential oil from peppermint have been used for health purposes. Peppermint oil is the essential oil taken from the flowering parts and leaves of the peppermint plant. (Essential oils are very concentrated oils containing substances that give a plant its characteristic odor or flavor.)\n" +
                        "Peppermint is a common flavoring agent in foods and beverages, and peppermint oil is used as a fragrance in soaps and cosmetics.\n" +
                        "Peppermint has been used for health purposes for several thousand years. Records from ancient Greece, Rome, and Egypt mention that it was used for digestive disorders and other conditions.")
            }
            if(receivedData=="Dalchinni"){
                image.setImageResource(R.drawable.dalchinnipic)
                plant.setText("Dalchinni")
                description.setText("Cinnamon (Dalchini) is a dried bark of a small tree (Cinnamomum Zeylanicum) growing mainly in western/southern parts of India. It is one of the oldest and important spices available in Indian kitchens as part of ‘Garam Masala’. It is characterized by special flavor and aroma due to its essential oils. It is available in market in dried and rolled sticks. Its leaves are commonly called as ‘Tejpatra’.\n" +
                        "Dalchini has been described as ‘Tvak’ in Ayurveda and used in various ailments like flu, indigestion, edema and cough etc. As per Ayurveda, its taste is pungent, sweet and hot in nature. It is light, rough, penetrating and often recommended for people with the Kaphavata constitution.\n")
            }
            if(receivedData=="Basil"){
                image.setImageResource(R.drawable.basilpic)
                plant.setText("Basil")
                description.setText("Basil is an herb. The parts of the plant that grow above the ground are used to make medicine.\n" +
                        "\n" +
                        "Basil is used for stomach spasms, loss of appetite, intestinal gas, kidney conditions, fluid retention, head colds, warts, and worm infections. It is also used to treat snake and insect bites.\n" +
                        "\n" +
                        "Women sometimes use basil before and after childbirth to promote blood circulation, and also to start the flow of breast milk.\n" +
                        "\n" +
                        "Some people use it as a gargle.\n" +
                        "\n" +
                        "In foods, basil is used for flavor.\n" +
                        "\n")
            }
            if(receivedData=="Ashwagandha"){
                image.setImageResource(R.drawable.ashwagandhapic)
                plant.setText("Ashwagandha")
                description.setText("Ayurveda, the traditional system of medicine practiced in India can be traced back to 6000 BC (Charak Samhita, 1949). For most of these 6000 years Ashwagandha has been used as a Rasayana. The root of Ashwagandha is regarded as tonic, aphrodisiac, narcotic, diuretic, anthelmintic, astringent, thermogenic and stimulant. The root smells like horse (“ashwa”), that is why it is called Ashwagandha (on consuming it gives the power of a horse). It is commonly used in emaciation of children (when given with milk, it is the best tonic for children), debility from old age, rheumatism, vitiated conditions of vata, leucoderma, constipation, insomnia, nervous breakdown, goiter etc. (Sharma, 1999). The paste formed when roots are crushed with water is applied to reduce the inflammation at the joints (Bhandari, 1970). It is also locally applied in carbuncles, ulcers and painful swellings (Kritikar and Basu, 1935). The root in combination with other drugs is prescribed for snake venom as well as in scorpion-sting. It also helps in leucorrhoea, boils, pimples, flatulent colic, worms and piles (Misra, 2004). The Nagori Ashwagandha is the supreme among all Ashwagandha varieties. Maximum benefit appears when fresh Ashwagandha powder is used (Singh, 1983).\n" +
                        "\n" +
                        "The leaves are bitter and are recommended in fever, painful swellings. The flowers are astringent, depurative, diuretic and aphrodisiac. The seeds are anthelmintic and combined with astringent and rock salt remove white spots from the cornea. Ashwagandharishta prepared from it is used in hysteria, anxiety, memory loss, syncope, etc. It also acts as a stimulant and increases the sperm count (Sharma, 1938).\n")
            }

        }

    }
}