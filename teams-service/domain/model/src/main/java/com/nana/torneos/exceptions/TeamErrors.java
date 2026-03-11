package com.nana.torneos.exceptions;

public class TeamErrors {

    public static BusinessException teamNotFound() {
        return new BusinessException(
                "Team not found",
                "TEAM_NOT_FOUND",
                404
        );
    }

    public static BusinessException teamIdIsRequired() {
        return new BusinessException(
                "Team id cant be empty",
                "TEAM_ID_REQUIRED",
                400
        );
    }

    public static BusinessException requiredFields() {
        return new BusinessException(
                "All fields (name, acronym, creatorId) are required",
                "TEAM_FIELDS_REQUIRED",
                400
        );
    }

    public static BusinessException nameOrAcronymInUse() {
        return new BusinessException(
                "Team name or acronym already in use",
                "TEAM_ALREADY_EXISTS",
                409
        );
    }

    public static BusinessException notOwner() {
        return new BusinessException(
                "You don't have permissions to modify this team",
                "TEAM_FORBIDDEN",
                403
        );
    }

    public static BusinessException ownerCannotLeave() {
        return new BusinessException(
                "Team owner cannot leave the team",
                "TEAM_OWNER_CANNOT_LEAVE",
                400
        );
    }

    public static BusinessException ownerCannotRemoveSelf() {
        return new BusinessException(
                "Team owner cannot remove themselves",
                "TEAM_OWNER_CANNOT_REMOVE_SELF",
                400
        );
    }

    public static BusinessException userAlreadyInTeam() {
        return new BusinessException(
                "User already belongs to a team",
                "USER_ALREADY_IN_TEAM",
                409
        );
    }

    public static BusinessException userIsOwnerNoNeedToJoin() {
        return new BusinessException(
                "You are the owner of this team, you don't need to register",
                "USER_IS_TEAM_OWNER",
                400
        );
    }

    public static BusinessException userNotInThisTeam() {
        return new BusinessException(
                "This user does not belong to this team",
                "USER_NOT_IN_TEAM",
                409
        );
    }
    public static BusinessException userIdRequired() {
        return new BusinessException(
                "User id cant be empty",
                "USER_ID_REQUIRED",
                400
        );
    }


    public static BusinessException userNotFound() {
        return new BusinessException(
                "User not found",
                "USER_NOT_FOUND",
                404
        );
    }

    public static BusinessException forbidden() {
        return new BusinessException(
                "You don't have permissions to modify this team",
                "TEAM_FORBIDDEN",
                403
        );
    }


    public static BusinessException userNotInTeam() {
        return new BusinessException(
                "User does not belong to this team",
                "USER_NOT_IN_TEAM",
                409
        );
    }

    public static BusinessException requesterIdRequired() {
        return new BusinessException(
                "Requester id cant be empty",
                "REQUESTER_ID_REQUIRED",
                400
        );
    }
}

