package post.vote;

import java.util.UUID;

public record Vote(UUID postId, UUID userId, VoteType voteType) {
}
