package post.vote;

import java.util.UUID;

public record PostVote(UUID postId, UUID userId, VoteType voteType) {
}
