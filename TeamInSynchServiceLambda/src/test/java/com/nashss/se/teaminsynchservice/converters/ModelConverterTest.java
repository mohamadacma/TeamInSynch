package com.nashss.se.teaminsynchservice.converters;

import com.nashss.se.teaminsynchservice.dynamodb.models.Member;
import com.nashss.se.teaminsynchservice.models.MemberModel;
import com.nashss.se.teaminsynchservice.models.WeatherModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelConverterTest {
    private ModelConverter modelConverter = new ModelConverter();

    @Test
    void toWeatherModel_convertsWeatherData() {
        // GIVEN
        Double latitude = 35.6895;
        Double longitude = 139.6917;
        String time = "2024-06-17T12:00:00Z";
        String weatherDescription = "Clear sky";
        Double maxTemperature = 25.0;
        Double minTemperature = 15.0;

        // WHEN
        WeatherModel weatherModel = modelConverter.toWeatherModel(latitude, longitude, time, weatherDescription, maxTemperature, minTemperature);

        // THEN
        assertEquals(latitude, weatherModel.getLatitude());
        assertEquals(longitude, weatherModel.getLongitude());
        assertEquals(time, weatherModel.getTime());
        assertEquals(weatherDescription, weatherModel.getWeatherDescription());
        assertEquals(maxTemperature, weatherModel.getMaxTemperature());
        assertEquals(minTemperature, weatherModel.getMinTemperature());
    }

    @Test
    void toMemberModel_convertsMember() {
        // GIVEN
        Member member = new Member();
        member.setMemberId("id");
        member.setMemberName("John Doe");
        member.setMemberEmail("john.doe@example.com");
        member.setPhoneNumber("123-456-7890");
        member.setCity("Tokyo");
        member.setJoinDate("2024-01-01");
        member.setBackground("Software Engineer");
        member.setRole("Developer");
        member.setTeamName("Team A");

        // WHEN
        MemberModel memberModel = modelConverter.toMemberModel(member);

        // THEN
        assertEquals(member.getMemberId(), memberModel.getMemberId());
        assertEquals(member.getMemberName(), memberModel.getMemberName());
        assertEquals(member.getMemberEmail(), memberModel.getMemberEmail());
        assertEquals(member.getPhoneNumber(), memberModel.getPhoneNumber());
        assertEquals(member.getCity(), memberModel.getCity());
        assertEquals(member.getJoinDate(), memberModel.getJoinDate());
        assertEquals(member.getBackground(), memberModel.getBackground());
        assertEquals(member.getRole(), memberModel.getRole());
        assertEquals(member.getTeamName(), memberModel.getTeamName());
    }

    @Test
    void toMemberModelList_convertsListOfMembers() {
        //GIVEN
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Member member = new Member();
            member.setMemberId("id" + i);
            member.setMemberName("Member " + i);
            member.setMemberEmail("member" + i + "@example.com");
            member.setPhoneNumber("123-456-78" + i);
            member.setCity("City " + i);
            member.setJoinDate("2024-01-0" + i);
            member.setBackground("Background " + i);
            member.setRole("Role " + i);
            member.setTeamName("Team " + i);
            members.add(member);
        }

        // WHEN
        List<MemberModel> memberModels = modelConverter.toMemberModelList(members);

        // THEN
        assertEquals(members.size(), memberModels.size());
        for (int i = 0; i < members.size(); i++) {
            assertEquals(members.get(i).getMemberId(), memberModels.get(i).getMemberId());
            assertEquals(members.get(i).getMemberName(), memberModels.get(i).getMemberName());
            assertEquals(members.get(i).getMemberEmail(), memberModels.get(i).getMemberEmail());
            assertEquals(members.get(i).getPhoneNumber(), memberModels.get(i).getPhoneNumber());
            assertEquals(members.get(i).getCity(), memberModels.get(i).getCity());
            assertEquals(members.get(i).getJoinDate(), memberModels.get(i).getJoinDate());
            assertEquals(members.get(i).getBackground(), memberModels.get(i).getBackground());
            assertEquals(members.get(i).getRole(), memberModels.get(i).getRole());
            assertEquals(members.get(i).getTeamName(), memberModels.get(i).getTeamName());
        }
    }
}
