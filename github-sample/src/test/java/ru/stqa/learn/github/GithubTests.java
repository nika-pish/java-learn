package ru.stqa.learn.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.apache.commons.lang3.CharSetUtils.count;

public class GithubTests {

  @Test
  public void testCommits() throws IOException {
    Github github = new RtGithub("4784030f0c1bd5431ada69a60a14065271570f3e ");
    RepoCommits commits = github.repos()
            .get(new Coordinates.Simple("nika-pish", "java-learn")).commits();
    for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
      System.out.println(new RepoCommit.Smart(commit).message());
    }
  }

}
