package com.example.donorpath;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;
public class NgoDirectory extends Fragment {

    private List<Ngo> ngoList; // Full list of NGOs
    private NgoAdapter adapter; // Adapter for RecyclerView

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ngo_directory, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewNgo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText searchBar = view.findViewById(R.id.search_bar);

        // Temporary static list of NGOs
        ngoList = new ArrayList<>();
        ngoList.add(new Ngo("Acumen", "Invests in sustainable businesses to tackle poverty","Raises charitable donations for companies/leaders/ideas that are working on poverty issues.\n" +
                "Corporations can partner with Acumen to provide financial and human capital. Partners can participate" +
                "in multiple networking events, reports, and more." ,R.drawable.logo1, "https://donate.acumen.org/give/639704/?_gl=1*1st70pe*_ga*MTQwMDM0OTUxOC4xNzM1NDM2MTQw#!/donation/checkout?c_src=website&c_src2=donate-once"));
        ngoList.add(new Ngo("Unicef - Malaysia", "Advocates for children's rights and provides humanitarian aid"," We protect children's rights and safeguard their futures." +
                "Protect children from violence and abuse. Bring clean water and sanitation to those in need. And keep them safe from climate change and disease. The world's largest provider of vaccines, UNICEF also runs the world's largest humanitarian warehouse." ,R.drawable.logo2, "https://help.unicef.org/malaysia/help-children-emergencies?utm_source=google&utm_medium=cpc&utm_campaign=CRE_MY_GOOGLE_SEARCH_BRAND_PROS_CONV_ALL_160123&utm_content=adid_705937232789&utm_term=adgroupid_144748040869&gad_source=1&gclid=Cj0KCQiA7NO7BhDsARIsADg_hIazaPlGmDSN-Q2APq-QcnvwSDSn-mScEK_qQdex0XLsfYMsQ3VIN20aAnIZEALw_wcB"));
        ngoList.add(new Ngo("Save the Children", "Supports children in need, including refugees,with education and healthcare","International non-governmental organization that promotes children’s rights, provides relief, provides" +
                "quality education and preschool programs, books, supplies, educational toys and helps support children in" +
                "developing countries." ,R.drawable.logo3, "https://support.savethechildren.org/site/Donation2?idb=749810867&df_id=1620&mfc_pref=T&1620.donation=form1&1620_donation=form1"));
        ngoList.add(new Ngo("Plan International", "Promotes children's rights and gender equality"," Promotes and protects children’s rights by building powerful partnerships and alliances in and across" +
                "communities, and from the local to the regional and global level." ,R.drawable.logo4, "https://plan-international.org/get-involved/"));
        ngoList.add(new Ngo("World Vision", "Focuses on child welfare, education, and disaster relief","Global Christian relief, development and advocacy organization dedicated to working with children, families" +
                "and communities to overcome poverty and injustice; uses an impactful community development approach to" +
                "empower children and families to break the cycle of poverty" ,R.drawable.logo5, "https://www.worldvision.com.my/en/sponsor-a-child/spark-hope-this-chinese-new-year?mc=15531&utm_term=charity%20organizations&utm_campaign=1000+Girls+CS+GENERIC+(SEM)&utm_source=google&utm_medium=paid-google-sem-fishermen&utm_content=sem-cny-e&hsa_acc=9466289323&hsa_cam=20371053484&hsa_grp=157948847731&hsa_ad=727641586942&hsa_src=g&hsa_tgt=kwd-11443022&hsa_kw=charity%20organizations&hsa_mt=b&hsa_net=adwords&hsa_ver=3&gad_source=1&gclid=Cj0KCQiA7NO7BhDsARIsADg_hIaxchJ7e_OvAc99XI8oulsyYoqqB2C5o3Al4QMMjnTXLMNIoHvXPAsaApQqEALw_wcB"));
        ngoList.add(new Ngo("Brac", "Empowers communities through education, healthcare, and economic development","Develops support services in human rights and social empowerment, education and health, economic empowerment" +
                "and enterprise development, livelihood training, environmental sustainability and disaster preparedness.", R.drawable.logo6, "https://bracinternational.org/donate/"));
        ngoList.add(new Ngo("Care", "Fights global poverty and supports humanitarian relief efforts","Main areas of work include HIV/AIDS, food security, education, gender equality, climate change mitigation, water and" +
                "sanitation, and economic development in developing and least-developed countries." ,R.drawable.logo7, "https://my.care.org/site/Donation2?44795.donation=form1&df_id=44795&mfc_pref=T&s_src=1725202LC000&s_subsrc=FY25COREDFDECLCMNAVDONATEBTN&_gl=1*1fq4xnr*_gcl_au*MzcyNzQ1MDU5LjE3MzU4MDI0MTA.*_ga*MTUzNDAyODMyMS4xNzM1ODAyNDEw*_ga_BE6GP7SC1M*MTczNTgwMjQxMC4xLjAuMTczNTgwMjQxMC42MC4wLjA.&_ga=2.174117730.1391961194.1735802410-1534028321.1735802410"));
        ngoList.add(new Ngo("Heifer International", "Provides livestock and training to combat hunger and poverty", "Works to eradicate poverty and hunger through sustainable, values-based holistic community development." +
                "Distributes animals, along with agricultural and values-based training, to families in need around the world" +
                "as a means of providing self-sufficiency",R.drawable.logo8, "https://www.heifer.org/give/one-time.html"));
        ngoList.add(new Ngo("One Acre Fund", "Supports smallholder farmers with tools and training to increase productivity","Seeks to eradicate poverty and end hunger by empowering smallholder farms in developing countries." +
                "Methods to achieve goals include financing for farm inputs, distribution of seed and fertilizer, training on" +
                "agricultural techniques and market facilitation to maximize profits from harvest sales." ,R.drawable.logo9, "https://oneacrefund.org/"));
        ngoList.add(new Ngo("Partners in Health", "Delivers healthcare to impoverished communities worldwide","Works closely with the local government of nations and their medical and academic institutions to provide a" +
                "preferential option for the poor in health care. Establishes long-term relationships with sister organizations" +
                "based in settings of poverty and strives to bring the benefits of modern medical science to those most in" +
                "need of them and to serve as an antidote to despair." ,R.drawable.logo10, "https://www.pih.org/"));
        ngoList.add(new Ngo("Marie Stopes International", "Offers reproductive healthcare services and education","Provides world’s poorest and most vulnerable women with quality family planning and reproductive" +
                "healthcare. Works with individuals in remote rural areas and in urban slums whose poor access to family" +
                "planning and reproductive health care only exacerbates their poverty and vulnerability." ,R.drawable.logo11, "https://www.msichoices.org/"));
        ngoList.add(new Ngo("Fonkoze", "Empowers Haitian communities through microfinance and education", "Haiti’s largest microfinance institution, offering a full range of financial and development services to Haiti’s" +
                "rural poor. Provides women with the required resources and job training to escape poverty. ",R.drawable.logo12, "https://fonkoze.org/"));



        adapter = new NgoAdapter(ngoList, getParentFragmentManager());
        recyclerView.setAdapter(adapter);

        // Add TextWatcher to filter results
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });

        return view;
    }

    // Filter method to search NGO names
    private void filter(String text) {
        List<Ngo> filteredList = new ArrayList<>();
        for (Ngo ngo : ngoList) {
            if (ngo.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(ngo);
            }
        }
        adapter.updateList(filteredList);
    }
}


